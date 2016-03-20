package flair.gradle.tasks

import flair.gradle.tasks.process.*

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public enum TaskDefinition {
	CLEAN( "clean" , Clean , TaskGroup.BUILD ) ,
	PROCESS_ASSETS( "processAssets" , ProcessAssets , TaskGroup.PROCESS ) ,
	PROCESS_RESOURCES( "processResources" , ProcessResources , TaskGroup.PROCESS ) ,
	PROCESS_SPLASHS( "processSplashs" , ProcessSplashs , TaskGroup.PROCESS ) ,
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
	ASDOC( "asdoc" , Asdoc , TaskGroup.DOCUMENTATION ) ,
	CERTIFICATE( "certificate" , Certificate , TaskGroup.SIGNING ) ,
	LIST_DEVICES( "listDevices" , ListDevices , TaskGroup.DEVICES )

	private String name

	private Class type

	private TaskGroup group

	public TaskDefinition( String name , Class type , TaskGroup group )
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

	public TaskGroup getGroup()
	{
		return group
	}
}