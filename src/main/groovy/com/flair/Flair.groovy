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
		project.getTasks( ).create( "generate project" , Scaffold )
		project.getTasks( ).create( "copy ios resources" , CopyIOSResources )
		project.getTasks( ).create( "copy android resources" , CopyAndroidResources )
		project.getTasks( ).create( "copy desktop resources" , CopyDesktopResources )
		project.getTasks( ).create( "generate textures" , TexturePacker )
		project.getTasks( ).create( "write version" , VersionHuntingWriteVersion )
		project.getTasks( ).create( "increment version" , VersionHuntingIncrementVersion )
	}
}
