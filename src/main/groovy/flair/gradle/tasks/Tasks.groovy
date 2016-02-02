package flair.gradle.tasks

import flair.gradle.tasks.process.*

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public enum Tasks {
	CLEAN( "clean" , Clean , Groups.BUILD ) ,
	PROCESS_ASSETS( "processAssets" , ProcessAssets , Groups.PROCESS ) ,
	PROCESS_RESOURCES( "processResources" , ProcessResources , Groups.PROCESS ) ,
	PROCESS_SPLASHS( "processSplashs" , ProcessSplashs , Groups.PROCESS ) ,
	PROCESS_ICONS( "processIcons" , ProcessIcons , Groups.PROCESS ) ,
	PROCESS_EXTENSIONS( "processExtensions" , ProcessExtensions , Groups.PROCESS ) ,
	PROCESS_APP_DESCRIPTOR( "processAppDescriptor" , ProcessAppDescriptor , Groups.PROCESS ) ,
	PROCESS_CLASSES( "processClasses" , ProcessClasses , Groups.PROCESS ) ,
	PROCESS_LIBRARIES( "processLibraries" , ProcessLibraries , Groups.PROCESS ) ,
	PROCESS_AS_LIBRARIES( "processAsLibraries" , ProcessAsLibraries , Groups.PROCESS ) ,
	ASSEMBLE( "assemble" , VariantContainer , Groups.BUILD ) ,
	COMPILE( "compile" , Compile , Groups.BUILD ) ,
	PACKAGE( "package" , Package , Groups.PACKAGE ) ,
	INSTALL( "install" , Install , Groups.INSTALL ) ,
	UNINSTALL( "uninstall" , Uninstall , Groups.UNINSTALL ) ,
	LAUNCH_ADL( "launchAdl" , LaunchAdl , Groups.LAUNCH ) ,
	LAUNCH_DEVICE( "launchDevice" , LaunchDevice , Groups.LAUNCH ) ,
	PUBLISH_ATLASES( "publishAtlases" , PublishAtlases , Groups.TEXTURE_PACKER ) ,
	ASDOC( "asdoc" , Asdoc , Groups.DOCUMENTATION ) ,
	CERTIFICATE( "certificate" , Certificate , Groups.SIGNING ) ,
	LIST_DEVICES( "listDevices" , ListDevices , Groups.DEVICES )

	private String name

	private Class type

	private Groups group

	public Tasks( String name , Class type , Groups group )
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

	public Groups getGroup()
	{
		return group
	}
}