package com.ffaero.openrocketassembler.controller.actions

import com.ffaero.openrocketassembler.controller.ProjectController
import com.ffaero.openrocketassembler.controller.ProjectAdapter
import com.google.protobuf.ByteString
import com.ffaero.openrocketassembler.controller.ConfigurationAdapter
import com.ffaero.openrocketassembler.controller.ConfigurationController
import java.io.File

class DefaultComponentTemplate : ActionBase<ProjectController>() {
	private val template = ByteString.copyFrom(byteArrayOf(
		 0x1F, -0x75,  0x08,  0x00,  0x77,  0x19, -0x0A,  0x5F,  0x00,  0x03, -0x4D, -0x4F, -0x51, -0x38, -0x33,
		 0x51,  0x28,  0x4B,  0x2D,  0x2A, -0x32, -0x34, -0x31, -0x4D,  0x55,  0x32, -0x2C,  0x33,  0x50,  0x52,
		 0x48, -0x33,  0x4B, -0x32,  0x4F, -0x37, -0x34,  0x4B, -0x49,  0x55,  0x0A,  0x0D,  0x71, -0x2D, -0x4B,
		 0x50, -0x4E, -0x49, -0x1D, -0x1B, -0x4E, -0x37,  0x2F,  0x48, -0x33,  0x2B, -0x36,  0x4F, -0x32,  0x4E,
		 0x2D,  0x41,  0x55,  0x6D,  0x67,  0x03,  0x11, -0x4B, -0x4D,  0x29,  0x2E,  0x4D,  0x4A, -0x32, -0x31,
		 0x2D, -0x38, -0x31,  0x4B, -0x33,  0x2B,  0x29,  0x06,  0x72,  0x4B,  0x12, -0x2D,  0x53, -0x0B, -0x13,
		 0x6C, -0x0C, -0x2F, -0x3C, -0x0B,  0x61, -0x16, -0x0B,  0x11,  0x46,  0x02,  0x2D,  0x00,  0x00, -0x43,
		 0x0C,  0x20, -0x5C, -0x77,  0x00,  0x00,  0x00
	))
	
	private val projectListener = object : ProjectAdapter() {
		override fun onComponentTemplateChange(sender: ProjectController) = enqueueAction(sender)
	}
	
	private val configListener = object : ConfigurationAdapter() {
		override fun onConfigurationsReset(sender: ConfigurationController, names: List<String>) = enqueueAction(sender.proj)
		override fun onConfigurationAdded(sender: ConfigurationController, index: Int, name: String, components: List<File>) = enqueueAction(sender.proj)
	}
	
	override fun runAction(controller: ProjectController) {
		if (controller.componentTemplate.isEmpty()) {
			controller.componentTemplate = template
		}
		controller.configurations.names.forEachIndexed { it, _ ->
			if (controller.configurations.getFileOutlineAt(it).isEmpty()) {
				controller.configurations.setFileOutlineAt(it, template)
			}
		}
	}

	override fun addListeners(controller: ProjectController) {
		controller.addListener(projectListener)
		controller.configurations.addListener(configListener)
		enqueueAction(controller)
	}

	override fun removeListeners(controller: ProjectController) {
		controller.removeListener(projectListener)
		controller.configurations.removeListener(configListener)
	}
}
