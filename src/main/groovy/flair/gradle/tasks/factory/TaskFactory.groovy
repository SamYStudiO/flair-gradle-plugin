package flair.gradle.tasks.factory

import flair.gradle.platforms.Platform
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface TaskFactory<T>
{
	public T create( Project project , String prefix , Platform platform , boolean singlePlatform , List<String> dependencies )
}