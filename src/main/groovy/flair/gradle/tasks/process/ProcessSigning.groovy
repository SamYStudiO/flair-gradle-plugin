package flair.gradle.tasks.process

import flair.gradle.extensions.FlairProperty
import flair.gradle.tasks.AbstractVariantTask
import flair.gradle.tasks.TaskGroup
import flair.gradle.utils.Platform
import flair.gradle.utils.Variant
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessSigning extends AbstractVariantTask
{
	@InputFiles
	def Set<File> inputFiles

	@OutputDirectory
	def File outputDir

	@Input
	def String packageTarget

	@Input
	def String signingStoreType

	@Input
	def String signingKeyStore

	@Input
	def String signingProvisioningProfile

	@Input
	def String signingStorePass

	@Override
	public void setVariant( Variant variant )
	{
		super.variant = variant

		packageTarget = extensionManager.getFlairProperty( variant , FlairProperty.PACKAGE_TARGET )
		signingStoreType = extensionManager.getFlairProperty( variant , FlairProperty.SIGNING_STORE_TYPE )
		signingKeyStore = extensionManager.getFlairProperty( variant , FlairProperty.SIGNING_KEY_STORE ) ?: "null"
		signingProvisioningProfile = extensionManager.getFlairProperty( variant , FlairProperty.SIGNING_PROVISIONING_PROFILE ) ?: "null"
		signingStorePass = extensionManager.getFlairProperty( variant , FlairProperty.SIGNING_STORE_PASS ) ?: "null"
		inputFiles = findInputFiles( )
		outputDir = project.file( "${ outputVariantDir }/signing" )

		description = "Processes signing files into ${ variant.name } ${ project.buildDir.name } directory"
	}

	public ProcessSigning()
	{
		group = TaskGroup.DEFAULT.name
	}

	@TaskAction
	public void processSigning()
	{
		outputDir.deleteDir( )

		for( File file : inputFiles )
		{
			if( file.exists( ) )
			{
				project.copy {
					from file
					into outputDir

					exclude "*.txt"

					if( outputDir.listFiles( ).find {
						it.name.split( "\\." )[ 1 ].toLowerCase( ) == "p12"
					} || signingStoreType != "pkcs12" ) exclude "*.p12"
					if( outputDir.listFiles( ).find { it.name.split( "\\." )[ 1 ].toLowerCase( ) == "mobileprovision" } ) exclude "*.mobileprovision"
				}

				if( file.isDirectory( ) ) break
			}
		}
	}

	private Set<File> findInputFiles()
	{
		List<File> list = new ArrayList<File>( )

		String subFolder = ""

		if( signingStoreType == "pkcs12" || variant.platform == Platform.IOS )
		{
			switch( true )
			{
				case variant.platform == Platform.IOS && packageTarget == "ipa-app-store":
					subFolder = "store"
					break
				case variant.platform == Platform.IOS && packageTarget == "ipa-ad-hoc":
					subFolder = "adhoc"
					break

				case variant.platform == Platform.IOS:
					subFolder = "development"
					break

				default:
					subFolder = ""
					break
			}
			variant.directories.each {
				list.add( project.file( "${ moduleDir }/src/${ it }/signing/${ subFolder }" ) )
			}
		}

		if( signingKeyStore != "null" ) list.add( project.file( signingKeyStore ) )
		if( signingProvisioningProfile != "null" && variant.platform == Platform.IOS ) list.add( project.file( signingProvisioningProfile ) )

		return list.reverse( )
	}
}
