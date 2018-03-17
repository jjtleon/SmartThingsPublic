/**
 *  Coleman SaluSpa
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
	definition (name: "Coleman SaluSpa", namespace: "jjtleon", author: "Joel Tleon") {
        capability "Sensor"
        
        attribute "filter", "enum", ["off", "on"]
        attribute "heat", "enum", ["off", "on"]
        attribute "massage", "enum", ["off", "on"]
        attribute "mode", "enum", ["idle", "filtering", "heating", "massaging"]
        
		command "setMode", ["string"]
	}

	simulator {
	}

	// 6 x Unlimited grid
	tiles(scale: 2) {
    	standardTile("mode", "device.mode", width: 6, height: 4) {
           	// Blue (#00a0dc) represents "on"-like device states
            // White (#ffffff) represents "off"-like device states
            // Orange (#e86d13) represents device states that require the user's attention
            // Gray (#cccccc) represents "inactive" or "offline" device states
            state "idle", label: '${name}', icon: "st.Health & Wellness.health2", backgroundColor: "#e86d13"  // orange
            state "filtering", label: '${name}', icon: "st.Health & Wellness.health2", backgroundColor: "#cccccc"  // gray
            state "heating", label: '${name}', icon: "st.Health & Wellness.health2", backgroundColor: "#ffffff"  // white
            state "massaging", label: '${name}', icon: "st.Health & Wellness.health2", backgroundColor: "#00a0dc"  // blue
        }
    	standardTile("filter", "device.filter", width: 2, height: 2) {
            state "off", label: '${name}', icon: "st.Outdoor.outdoor8", backgroundColor: "#ffffff"  // white
            state "on", label: '${name}', icon: "st.Outdoor.outdoor8", backgroundColor: "#00a0dc"  // blue
        }
    	standardTile("heat", "device.heat", width: 2, height: 2) {
            state "off", label: '${name}', icon: "st.Weather.weather14", backgroundColor: "#ffffff"  // white
            state "on", label: '${name}', icon: "st.Weather.weather14", backgroundColor: "#00a0dc"  // blue
        }
    	standardTile("massage", "device.massage", width: 2, height: 2) {
            state "off", label: '${name}', icon: "st.People.people5", backgroundColor: "#ffffff"  // white
            state "on", label: '${name}', icon: "st.People.people5", backgroundColor: "#00a0dc"  // blue
        }
        
        // The "mode" tile will appear in the Things view
        main("mode")
        
        // The "mode", "filter", "heat" and "massage" tiles will appear in the Device Details view
        details(["mode", "filter", "heat", "massage"])
	}
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
}

// handle commands
def setMode(mode) {
    if (mode == "idle") {
		log.debug "Setting mode to idle"
	    sendEvent(name: "filter", value: "off")
	    sendEvent(name: "heat", value: "off")
	    sendEvent(name: "massage", value: "off")
	    sendEvent(name: "mode", value: "idle")
    } else if (mode == "filtering") {
		log.debug "Setting mode to filtering"
	    sendEvent(name: "filter", value: "on")
	    sendEvent(name: "heat", value: "off")
	    sendEvent(name: "massage", value: "off")
	    sendEvent(name: "mode", value: "filtering")
    } else if (mode == "heating") {
		log.debug "Setting mode to heating"
	    sendEvent(name: "filter", value: "on")
	    sendEvent(name: "heat", value: "on")
	    sendEvent(name: "massage", value: "off")
	    sendEvent(name: "mode", value: "heating")
    } else if (mode == "massaging") {
		log.debug "Setting mode to massaging"
	    sendEvent(name: "filter", value: "on")
	    sendEvent(name: "heat", value: "off")
	    sendEvent(name: "massage", value: "on")
	    sendEvent(name: "mode", value: "massaging")
    } else {
		log.debug "Unknown mode: ${mode}"
    }
}