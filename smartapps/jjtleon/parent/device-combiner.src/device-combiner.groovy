/**
 *  Device Combiner
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
    name: "Device Combiner",
    namespace: "jjtleon/parent",
    author: "Joel Tleon",
    description: "This SmartApp is the parent of Combiner Automations",
    category: "My Apps",
    singleInstance: true,
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")

preferences {
	// The parent app preferences are pretty simple: just use the app input for the child app
    page(name: "mainPage", title: "Device Combinations", install: true, uninstall: true, submitOnChange: true) {
        section {
            app(name: "deviceCombination", appName: "Combiner Automation", namespace: "jjtleon/combinations", title: "Create New Combination", multiple: true)
        }
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

// Nothing needed here since the child apps will handle preferences/subscriptions
def initialize() {
    // Just log some messages for information purposes
    log.debug "There are ${childApps.size()} child SmartApps"
    childApps.each { child ->
        log.debug "child app: ${child.label}"
    }
}
