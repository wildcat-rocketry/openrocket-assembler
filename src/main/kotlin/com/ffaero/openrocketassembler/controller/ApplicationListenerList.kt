package com.ffaero.openrocketassembler.controller

class ApplicationListenerList : ListenerListBase<ApplicationListener>(), ApplicationListener {
	override fun onBackgroundStatus(sender: ApplicationController, status: String) = forEach { it.onBackgroundStatus(sender, status) }
	override fun onProjectAdded(sender: ApplicationController, project: ProjectController) = forEach { it.onProjectAdded(sender, project) }
	override fun onProjectRemoved(sender: ApplicationController, project: ProjectController) = forEach { it.onProjectRemoved(sender, project) }
	override fun onWindowSplitChanged(sender: ApplicationController, split: Float) = forEach { it.onWindowSplitChanged(sender, split) }
}
