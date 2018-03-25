/**
 *  Hot Tub Sensor
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
metadata {
	definition (name: "Hot Tub Sensor", namespace: "jjtleon", author: "Joel Tleon") {
        capability "Sensor"
        capability "Switch"
        capability "Temperature Measurement"
        
		attribute "hotTubFilterMode", "enum", ["off", "on"]
        attribute "hotTubHeatMode", "enum", ["off", "on"]
        attribute "hotTubMassageMode", "enum", ["off", "on"]
		attribute "hotTubOperatingState", "enum", ["idle", "filtering", "heating", "massaging"]
        attribute "tempText", "string"
        
		command "setHotTubFilterMode", ["enum"]
		command "setHotTubHeatMode", ["enum"]
		command "setHotTubMassageMode", ["enum"]        
        command "setTemperature", ["number"]
	}

	simulator {
	}

	// 6 x Unlimited grid
	tiles(scale: 2) {
        multiAttributeTile(name: "state", type: "generic", width: 6, height: 4) {
        	tileAttribute("device.hotTubOperatingState", key: "PRIMARY_CONTROL") {
            	attributeState "idle", label: '${name}', icon: "st.Health & Wellness.health2", backgroundColor: "#cccccc"  // gray
            	attributeState "filtering", label: '${name}', icon: "st.Health & Wellness.health2", backgroundColor: "#ffffff"  // white
            	attributeState "heating", label: '${name}', icon: "st.Health & Wellness.health2", backgroundColor: "#e86d13"  // orange
            	attributeState "massaging", label: '${name}', icon: "st.Health & Wellness.health2", backgroundColor: "#00a0dc"  // blue
            }
            tileAttribute("device.tempText", key: "SECONDARY_CONTROL") {
            	attributeState "tempText", label: '${currentValue}°'
            }
        }
        standardTile("filter", "device.hotTubFilterMode", width: 2, height: 2) {
            state "off", label: '${name}', icon: "st.Outdoor.outdoor8", backgroundColor: "#ffffff"  // white
            state "on", label: '${name}', icon: "st.Outdoor.outdoor8", backgroundColor: "#00a0dc"  // blue
        }
        standardTile("heat", "device.hotTubHeatMode", width: 2, height: 2) {
            state "off", label: '${name}', icon: "st.Weather.weather14", backgroundColor: "#ffffff"  // white
            state "on", label: '${name}', icon: "st.Weather.weather14", backgroundColor: "#00a0dc"  // blue
        }
        standardTile("massage", "device.hotTubMassageMode", width: 2, height: 2) {
            state "off", label: '${name}', icon: "st.People.people5", backgroundColor: "#ffffff"  // white
            state "on", label: '${name}', icon: "st.People.people5", backgroundColor: "#00a0dc"  // blue
        }
        
        // The "state" tile will appear in the Things view
        main("state")
        
        // The "state", "filter", "heat" and "massage" tiles will appear in the Device Details view
        details(["state", "filter", "heat", "massage"])
	}
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
}

// handle commands
def on() {
	log.debug "Executing 'on'"
}

def off() {
	log.debug "Executing 'off'"
}

def setHotTubFilterMode(mode) {
	log.debug "Setting hotTubFilterMode to ${mode}"
    sendEvent(name: "hotTubFilterMode", value: mode)
    updateHotTubOperatingState()
}

def setHotTubHeatMode(mode) {
	log.debug "Setting hotTubHeatMode to ${mode}"
    sendEvent(name: "hotTubHeatMode", value: mode)
	sendEvent(name: "switch", value: mode)
    updateHotTubOperatingState()
}

def setHotTubMassageMode(mode) {
	log.debug "Setting hotTubMassageMode to ${mode}"
    sendEvent(name: "hotTubMassageMode", value: mode)
    updateHotTubOperatingState()
}

def updateHotTubOperatingState() {
	if (device.currentValue("hotTubMassageMode") == "on") {
    	log.debug "Setting hotTubOperatingState to massaging"
    	sendEvent(name: "hotTubOperatingState", value: "massaging")
    } else if (device.currentValue("hotTubHeatMode") == "on") {
    	log.debug "Setting hotTubOperatingState to heating"
    	sendEvent(name: "hotTubOperatingState", value: "heating")
    } else if (device.currentValue("hotTubFilterMode") == "on") {
    	log.debug "Setting hotTubOperatingState to filtering"
    	sendEvent(name: "hotTubOperatingState", value: "filtering")
    } else {
    	log.debug "Setting hotTubOperatingState to idle"
    	sendEvent(name: "hotTubOperatingState", value: "idle")
    }
}

def setTemperature(temp) {
	log.debug "Setting temperature to ${temp}"
    sendEvent(name: "temperature", value: temp)
    sendEvent(name: "tempText", value: String.format("%3.2f", temp))
}