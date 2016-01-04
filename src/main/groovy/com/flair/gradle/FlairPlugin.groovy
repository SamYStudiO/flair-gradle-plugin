package com.flair.gradle

import com.flair.gradle.tasks.*
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author SamYStudiO on 08/11/2015.
 */
public class FlairPlugin implements Plugin<Project>
{
	@Override
	public void apply( Project project )
	{
		project.getExtensions( ).create( "flair" , FlairPluginProperties )
		project.getTasks( ).create( "generateProject" , Scaffold )
		project.getTasks( ).create( "updateProperties" , UpdateProperties )
		project.getTasks( ).create( "generateFontClass" , Fonts )
		project.getTasks( ).create( "processIOSResources" , ProcessIOSResources )
		project.getTasks( ).create( "processAndroidResources" , ProcessAndroidResources )
		project.getTasks( ).create( "processDesktopResources" , ProcessDesktopResources )
		project.getTasks( ).create( "processProdIOSResources" , ProcessProdIOSResources )
		project.getTasks( ).create( "processProdAndroidResources" , ProcessProdAndroidResources )
		project.getTasks( ).create( "processProdDesktopResources" , ProcessProdDesktopResources )
		project.getTasks( ).create( "generateAtlases" , TexturePacker )
		project.getTasks( ).create( "incrementVersion" , VersioningIncrementVersion )
		project.getTasks( ).create( "generateResourceClass" , Resources )
	}
}