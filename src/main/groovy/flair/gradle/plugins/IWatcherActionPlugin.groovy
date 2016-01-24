package flair.gradle.plugins

import flair.gradle.directoryWatcher.IWatcherAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public interface IWatcherActionPlugin
{
	Map<? , IWatcherAction> getWatcherActions()
}