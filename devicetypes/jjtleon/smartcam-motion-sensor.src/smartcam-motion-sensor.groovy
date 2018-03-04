/**
 *  SmartCam Motion Sensor
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
	definition (name: "SmartCam Motion Sensor", namespace: "jjtleon", author: "Joel Tleon") {
		capability "Motion Sensor"
        capability "Sensor"
		capability "Switch"
	}

	simulator {
	}

	// 6 x Unlimited grid
	tiles(scale: 2) {
		multiAttributeTile(name: "motion", type: "generic", width: 6, height: 4, canChangeIcon: false) {
        	tileAttribute("device.motion", key: "PRIMARY_CONTROL") {
            	// Blue (#00a0dc) represents "on"-like device states
                // White (#ffffff) represents "off"-like device states
                // Orange (#e86d13) represents device states that require the user's attention
                // Gray (#cccccc) represents "inactive" or "offline" device states
            	attributeState "active", label: "motion", icon: "st.motion.motion.active", backgroundColor: "#00a0dc"
            	attributeState "inactive", label: "no motion", icon: "st.motion.motion.inactive", backgroundColor: "#cccccc"
            }
        }
        
        // The "motion" tile will appear in the Things view
        main("motion")
        
        // The "motion" tile will appear in the Device Details view
        details(["motion"])
	}
    
    preferences {
    	input name: "delay", type: "number", title: "Automatically turn off (in seconds)", description: "After this number of seconds", required: true
    }
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
}

// handle commands
def on() {
	log.debug "Executing 'on'"
	sendEvent(name: "motion", value: "active")
    runIn(delay, off)
}

def off() {
	log.debug "Executing 'off'"
	sendEvent(name: "motion", value: "inactive")
}