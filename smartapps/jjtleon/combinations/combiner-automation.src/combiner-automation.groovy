/**
 *  Combiner Automation
 *
 *  Copyright 2018 Joel Tleon
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Combiner Automation",
    namespace: "jjtleon/combinations",
    author: "Joel Tleon",
    description: "This SmartApp combines the status of selected devices and summarizes as the status of a single device.",
    category: "My Apps",
    parent: "jjtleon/parent:Device Combiner",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")

preferences {
    section("Select devices to summarize:") {
        input name: "devs1", title: "Select from actuators:", type: "capability.actuator", multiple: true, required: false
        input name: "devs2", title: "Select from sensors: ", type: "capability.sensor", multiple: true, required: false
    }

    section("Select summary device:") {
        input name: "sumDev", title: "Select virtual switch:", type: "capability.switch", required: true
    }

    section("Select attribute to monitor:") {
        input name: "monAtt", title: "Enter attribute name:", type: "text", required: true
        input name: "desVal", title: "Enter desired attribute value", type: "text", required: true
    }
}

def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
	subscribe(devs1, monAtt, deviceHandler)
	subscribe(devs2, monAtt, deviceHandler)
}

def deviceHandler(evt) {
	// Returns a list of the monitored attribute values for all devices
    def curVals = getCurVals()
    
    // Returns a list of attribute values equal to desired value
    def desVals = curVals.findAll {
    	curVal -> curVal == desVal ? true : false
    }
    
    log.debug "${desVals.size()} of ${curVals.size()} attribute ${monAtt} are ${desVal}"
    
    if (desVals.size() == curVals.size()) {
    	sumDev.on()
    } else {
    	sumDev.off()
    }
}

def getCurVals() {
	def curVals = []
    
    if (devs1) {
    	curVals.addAll(devs1.currentValue(monAtt))
    } else if (devs2) {
    	curVals.addAll(devs2.currentValue(monAtt))
    }
    
    return curVals
}
