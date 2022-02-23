package org.serverct.parrot.parrotx.ui.feature

import org.serverct.parrot.parrotx.function.basicReader
import org.serverct.parrot.parrotx.function.getAdaptedList
import org.serverct.parrot.parrotx.function.getData
import org.serverct.parrot.parrotx.ui.MenuConfiguration
import org.serverct.parrot.parrotx.ui.MenuFeature
import org.serverct.parrot.parrotx.ui.feature.util.VariableProvider
import taboolib.module.ui.ClickEvent
import taboolib.platform.util.nextChat
import taboolib.platform.util.sendInfoMessage

object CompleterFeature : MenuFeature() {

    override val name: String = "Completer"

    override fun handle(config: MenuConfiguration, data: Map<*, *>, event: ClickEvent, vararg args: Any?) {
        val commands = data.getAdaptedList<String>("Commands") ?: require("Commands")
        val message = data.getData<String>("Message")
        val user = event.clicker

        user.closeInventory()
        user.sendInfoMessage(message)
        user.nextChat { input ->
            commands.map { context ->
                basicReader.replaceNested(context) {
                    if (this == "input") {
                        input
                    } else {
                        VariableProvider.Registry[this]?.produce(config, data, event, *args) ?: ""
                    }
                }
            }.forEach { user.performCommand(it) }
        }
    }

}