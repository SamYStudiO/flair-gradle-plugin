package flair.gradle.tasks

import flair.gradle.tasks.processes.*

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
enum TaskDefinition {
	CLEAN( "clean" , Clean , TaskGroup.BUILD ) ,
	PROCESS_ASSETS( "processAssets" , ProcessAssets , TaskGroup.PROCESS ) ,
	PROCESS_RESOURCES( "processResources" , ProcessResources , TaskGroup.PROCESS ) ,
	PROCESS_SPLASH_SCREENS( "processSplashScreens" , ProcessSplashScreens , TaskGroup.PROCESS ) ,
	PROCESS_SIGNING( "processSigning" , ProcessSigning , TaskGroup.PROCESS ) ,
	PROCESS_ICONS( "processIcons" , ProcessIcons , TaskGroup.PROCESS ) ,
	PROCESS_EXTENSIONS( "processExtensions" , ProcessExtensions , TaskGroup.PROCESS ) ,
	PROCESS_APP_DESCRIPTOR( "processAppDescriptor" , ProcessAppDescriptor , TaskGroup.PROCESS ) ,
	PROCESS_CLASSES( "processClasses" , ProcessClasses , TaskGroup.PROCESS ) ,
	PROCESS_LIBRARIES( "processLibraries" , ProcessLibraries , TaskGroup.PROCESS ) ,
	PROCESS_AS_LIBRARIES( "processAsLibraries" , ProcessAsLibraries , TaskGroup.PROCESS ) ,
	ASSEMBLE( "assemble" , VariantTask , TaskGroup.BUILD ) ,
	COMPILE( "compile" , Compile , TaskGroup.BUILD ) ,
	PREPARE_PACKAGE( "preparePackage" , PreparePackage , TaskGroup.DEFAULT ) ,
	PACKAGE( "package" , Package , TaskGroup.PACKAGE ) ,
	INSTALL( "install" , Install , TaskGroup.INSTALL ) ,
	UNINSTALL( "uninstall" , Uninstall , TaskGroup.UNINSTALL ) ,
	LAUNCH_ADL( "launchAdl" , LaunchAdl , TaskGroup.LAUNCH ) ,
	LAUNCH_DEVICE( "launchDevice" , LaunchDevice , TaskGroup.LAUNCH ) ,
	PUBLISH_ATLASES( "publishAtlases" , PublishAtlases , TaskGroup.TEXTURE_PACKER ) ,
	GENERATE_RESOURCES_CLASS( "generateResourcesClass" , GenerateResourcesClass , TaskGroup.GENERATED ) ,
	GENERATE_FONTS_CLASS( "generateFontsClass" , GenerateFontsClass , TaskGroup.GENERATED ) ,
	AS_DOC( "asdoc" , Asdoc , TaskGroup.DOCUMENTATION ) ,
	CERTIFICATE( "certificate" , Certificate , TaskGroup.SIGNING ) ,
	LIST_DEVICES( "listDevices" , ListDevices , TaskGroup.DEVICES )

	private String name

	private Class type

	private TaskGroup group

	TaskDefinition( String name , Class type , TaskGroup group )
	{
		this.name = name
		this.type = type
		this.group = group
	}

	String getName()
	{
		return name
	}

	Class getType()
	{
		return type
	}

	TaskGroup getGroup()
	{
		return group
	}
}