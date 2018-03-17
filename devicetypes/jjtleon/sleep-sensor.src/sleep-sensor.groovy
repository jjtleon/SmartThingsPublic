/**
 *  Sleep Sensor
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
	definition (name: "Sleep Sensor", namespace: "jjtleon", author: "Joel Tleon") {
    	capability "Sleep Sensor"
		capability "Switch"
        
        attribute "sleeping", "enum", ["not sleeping", "sleeping"]
	}

	simulator {
	}

	// 6 x Unlimited grid
	tiles(scale: 2) {
    	standardTile("sleeping", "device.sleeping", width: 6, height: 4) {
           	// Blue (#00a0dc) represents "on"-like device states
            // White (#ffffff) represents "off"-like device states
            // Orange (#e86d13) represents device states that require the user's attention
            // Gray (#cccccc) represents "inactive" or "offline" device states
            state "sleeping", label: '${name}', icon: "st.Bedroom.bedroom2", backgroundColor: "#00a0dc"  // blue
            state "not sleeping", label: '${name}', icon: "st.Health & Wellness.health12", backgroundColor: "#cccccc"  // gray
        }
        
        // The "sleeping" tile will appear in the Things view
        main("sleeping")
        
        // The "sleeping" tile will appear in the Device Details view
        details("sleeping")
	}
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
}

// handle commands
def on() {
	log.debug "Executing 'on'"
    sendEvent(name: "sleeping", value: "sleeping")
}

def off() {
	log.debug "Executing 'off'"
    sendEvent(name: "sleeping", value: "not sleeping")
}
