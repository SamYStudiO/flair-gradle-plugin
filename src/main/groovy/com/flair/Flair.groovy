package com.flair

import com.flair.tasks.*
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author SamYStudiO on 08/11/2015.
 */
public class Flair implements Plugin<Project>
{
	@Override
	public void apply( Project project )
	{
		project.getExtensions( ).create( "flair" , FlairProperties )
		project.getTasks( ).create( "generateProject" , Scaffold )
		project.getTasks( ).create( "updateProperties" , UpdateProperties )
		project.getTasks( ).create( "generateFontsClass" , Fonts )
		project.getTasks( ).create( "processIOSResources" , ProcessIOSResources )
		project.getTasks( ).create( "processAndroidResources" , ProcessAndroidResources )
		project.getTasks( ).create( "processDesktopResources" , ProcessDesktopResources )
		project.getTasks( ).create( "generateAtlases" , TexturePacker )
		project.getTasks( ).create( "incrementVersion" , VersioningIncrementVersion )
	}
}
