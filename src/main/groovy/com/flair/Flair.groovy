package com.flair

import com.flair.tasks.*
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author SamYStudiO on 08/11/2015.
 */
class Flair implements Plugin<Project>
{
	@Override
	public void apply( Project project )
	{
		project.getExtensions( ).create( "flair" , FlairProperties )
		project.getTasks( ).create( "scaffold" , Scaffold )
		project.getTasks( ).create( "copyIOSResources" , CopyIOSResources )
		project.getTasks( ).create( "copyAndroidResources" , CopyAndroidResources )
		project.getTasks( ).create( "copyDesktopResources" , CopyDesktopResources )
		project.getTasks( ).create( "texturePacker" , TexturePacker )
		project.getTasks( ).create( "versionHunting" , VersionHunting )
	}
}
