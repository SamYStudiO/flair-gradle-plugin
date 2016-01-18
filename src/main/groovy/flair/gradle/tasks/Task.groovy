package flair.gradle.tasks

import flair.gradle.tasks.build.Clean
import flair.gradle.tasks.generated.AutoGenerateFontClass
import flair.gradle.tasks.generated.AutoGenerateResourceClass
import flair.gradle.tasks.generated.GenerateFontClass
import flair.gradle.tasks.generated.GenerateResourceClass
import flair.gradle.tasks.others.VersioningIncrementVersion
import flair.gradle.tasks.scaffold.Scaffold
import flair.gradle.tasks.scaffold.UpdateProperties
import flair.gradle.tasks.texturepacker.TexturePacker

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public enum Task {

	SCAFFOLD( "generateProject" , Scaffold , Group.SCAFFOLD ) ,
	CLEAN( "clean" , Clean , Group.BUILD ) ,
	//PROCESS_IOS_RESOURCES( "processIOSResources" , ProcessIOSResources , Group.DEFAULT ) ,
			//PROCESS_ANDROID_RESOURCES( "processAndroidResources" , ProcessAndroidResources , Group.DEFAULT ) ,
			//PROCESS_DESKTOP_RESOURCES( "processDesktopResources" , ProcessDesktopResources , Group.DEFAULT ) ,
			//PROCESS_PROD_IOS_RESOURCES( "processProdIOSResources" , ProcessProdIOSResources , Group.DEFAULT ) ,
			//PROCESS_PROD_ANDROID_RESOURCES( "processProdAndroidResources" , ProcessProdAndroidResources , Group.DEFAULT ) ,
			//PROCESS_PROD_DESKTOP_RESOURCES( "processProdDesktopResources" , ProcessProdDesktopResources , Group.DEFAULT ) ,
			//BUILD( "compile" , Build , Group.BUILD ) ,
			//PACKAGE( "package" , Packaging , Group.PACKAGE ) ,
			//INSTALL( "install" , Install , Group.INSTALL ) ,
			//LAUNCH( "launch" , Launch , Group.LAUNCH ) ,
			PUBLISH_ATLASES( "publishAtlases" , TexturePacker , Group.TEXTURE_PACKER ) ,
	INCREMENT_VERSION( "incrementVersion" , VersioningIncrementVersion , Group.DEFAULT ) ,
	GENERATE_FONT_CLASS( "generateFontClass" , GenerateFontClass , Group.GENERATED ) ,
	AUTO_GENERATE_FONT_CLASS( "autoGenerateFontClass" , AutoGenerateFontClass , Group.GENERATED ) ,
	GENERATE_RESOURCE_CLASS( "generateResourceClass" , GenerateResourceClass , Group.GENERATED ) ,
	AUTO_GENERATE_RESOURCE_CLASS( "autoGenerateResourceClass" , AutoGenerateResourceClass , Group.GENERATED )

	private String name

	private Class type

	private Group group

	Task( String name , Class type , Group group )
	{
		this.name = name
		this.type = type
		this.group = group
	}

	public String getName()
	{
		return name
	}

	public Class getType()
	{
		return type
	}

	public Group getGroup()
	{
		return group
	}
}