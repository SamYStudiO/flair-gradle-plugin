package flair.gradle.plugins

import flair.gradle.directoryWatcher.IWatcherAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IWatcherActionPlugin
{
	public Map<? , IWatcherAction> getWatcherActions()
}