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
        capability "Temperature Measurement"
        
        attribute "hotTubOperatingState", "enum", ["idle", "filtering", "heating", "massaging"]
        
		command "setHotTubOperatingState", ["string"]
        command "setTemperature", ["number"]
	}

	simulator {
	}

	// 6 x Unlimited grid
	tiles(scale: 2) {
        multiAttributeTile(name: "state", type: "generic", width: 6, height: 4) {
        	tileAttribute("device.hotTubOperatingState", key: "PRIMARY_CONTROL") {
            	attributeState "idle", label: '${name}', icon: "st.Health & Wellness.health2", backgroundColor: "#cccccc"  // gray
            	attributeState "filtering", label: '${name}', icon: "st.Outdoor.outdoor8", backgroundColor: "#ffffff"  // white
            	attributeState "heating", label: '${name}', icon: "st.Weather.weather14", backgroundColor: "#e86d13"  // orange
            	attributeState "massaging", label: '${name}', icon: "st.People.people5", backgroundColor: "#00a0dc"  // blue
            }
            tileAttribute("device.temperature", key: "SECONDARY_CONTROL") {
            	attributeState "temperature", label: '${currentValue}°'
            }
        }
        
        // The "state" tile will appear in the Things view
        main("state")
        
        // The "state" tile will appear in the Device Details view
        details(["state"])
	}
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
}

// handle commands
def setHotTubOperatingState(state) {
	log.debug "Setting hotTubOperatingState to ${state}"
    sendEvent(name: "hotTubOperatingState", value: "${state}")
}

def setTemperature(temp) {
	log.debug "Setting temperature to ${temp}"
    sendEvent(name: "temperature", value: temp)
}