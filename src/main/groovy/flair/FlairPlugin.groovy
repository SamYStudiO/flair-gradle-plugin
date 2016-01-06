package flair

import flair.tasks.AutoGenerateFontClass
import flair.tasks.GenerateFontClass
import flair.tasks.ProcessAndroidResources
import flair.tasks.ProcessDesktopResources
import flair.tasks.ProcessIOSResources
import flair.tasks.ProcessProdAndroidResources
import flair.tasks.ProcessProdDesktopResources
import flair.tasks.ProcessProdIOSResources
import flair.tasks.AutoGenerateResourceClass
import flair.tasks.GenerateResourceClass
import flair.tasks.Scaffold
import flair.tasks.TexturePacker
import flair.tasks.UpdateProperties
import flair.tasks.VersioningIncrementVersion
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
