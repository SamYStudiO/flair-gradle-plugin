package flair.gradle

import flair.gradle.tasks.AutoGenerateFontClass
import flair.gradle.tasks.GenerateFontClass
import flair.gradle.tasks.ProcessAndroidResources
import flair.gradle.tasks.ProcessDesktopResources
import flair.gradle.tasks.ProcessIOSResources
import flair.gradle.tasks.ProcessProdAndroidResources
import flair.gradle.tasks.ProcessProdDesktopResources
import flair.gradle.tasks.ProcessProdIOSResources
import flair.gradle.tasks.AutoGenerateResourceClass
import flair.gradle.tasks.GenerateResourceClass
import flair.gradle.tasks.Scaffold
import flair.gradle.tasks.TexturePacker
import flair.gradle.tasks.UpdateProperties
import flair.gradle.tasks.VersioningIncrementVersion
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
		project.getTasks( ).create( "processIOSResources" , ProcessIOSResources )
		project.getTasks( ).create( "processAndroidResources" , ProcessAndroidResources )
		project.getTasks( ).create( "processDesktopResources" , ProcessDesktopResources )
		project.getTasks( ).create( "processProdIOSResources" , ProcessProdIOSResources )
		project.getTasks( ).create( "processProdAndroidResources" , ProcessProdAndroidResources )
		project.getTasks( ).create( "processProdDesktopResources" , ProcessProdDesktopResources )
		project.getTasks( ).create( "generateAtlases" , TexturePacker )
		project.getTasks( ).create( "incrementVersion" , VersioningIncrementVersion )
		project.getTasks( ).create( "generateFontClass" , GenerateFontClass )
		project.getTasks( ).create( "autoGenerateFontClass" , AutoGenerateFontClass )
		project.getTasks( ).create( "generateResourceClass" , GenerateResourceClass )
		project.getTasks( ).create( "autoGenerateResourceClass" , AutoGenerateResourceClass )
	}
}
