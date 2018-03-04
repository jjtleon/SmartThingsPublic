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
		capability "Switch"
	}

	simulator {
	}

	// 6 x Unlimited grid
	tiles(scale: 2) {
    	standardTile("sleeping", "device.switch", width: 6, height: 4, canChangeIcon: false) {
           	// Blue (#00a0dc) represents "on"-like device states
            // White (#ffffff) represents "off"-like device states
            // Orange (#e86d13) represents device states that require the user's attention
            // Gray (#cccccc) represents "inactive" or "offline" device states
            state "on", label: "sleeping", icon: "st.Bedroom.bedroom2", backgroundColor: "#00a0dc"
            state "off", label: "not sleeping", icon: "st.Health & Wellness.health12", backgroundColor: "#cccccc"
        }
        
        // The "sleeping" tile will appear in the Things view
        main "sleeping"
        
        // The "sleeping" tile will appear in the Device Details view
        details "sleeping"
	}
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
}

// handle commands
def on() {
	log.debug "Executing 'on'"
	sendEvent(name: "switch", value: "on")
}

def off() {
	log.debug "Executing 'off'"
	sendEvent(name: "switch", value: "off")
}
