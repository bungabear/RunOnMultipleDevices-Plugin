package action

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class RunOnMultipleDevicesPopupAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val deviceAction = ActionManager.getInstance().getAction("DeviceAndSnapshotComboBox")
        try {
            val field = deviceAction.javaClass.declaredFields.first { it.name == "myRunOnMultipleDevicesAction" }
            field.isAccessible = true
            val targetAction = field.get(deviceAction) as AnAction
            targetAction.actionPerformed(e)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}