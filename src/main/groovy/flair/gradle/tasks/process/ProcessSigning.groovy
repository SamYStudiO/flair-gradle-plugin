package flair.gradle.tasks.process

import flair.gradle.extensions.FlairProperties
import flair.gradle.tasks.AbstractVariantTask
import flair.gradle.tasks.Groups
import flair.gradle.variants.Platforms
import flair.gradle.variants.Variant
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

		packageTarget = extensionManager.getFlairProperty( variant , FlairProperties.PACKAGE_TARGET )
		signingStoreType = extensionManager.getFlairProperty( variant , FlairProperties.SIGNING_STORE_TYPE )
		signingKeyStore = extensionManager.getFlairProperty( variant , FlairProperties.SIGNING_KEY_STORE ) ?: "null"
		signingProvisioningProfile = extensionManager.getFlairProperty( variant , FlairProperties.SIGNING_PROVISIONING_PROFILE ) ?: "null"
		signingStorePass = extensionManager.getFlairProperty( variant , FlairProperties.SIGNING_STORE_PASS ) ?: "null"
		inputFiles = findInputFiles( )
		outputDir = project.file( "${ outputVariantDir }/signing" )
	}

	public ProcessSigning()
	{
		group = Groups.DEFAULT.name
		description = ""
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

		if( signingStoreType == "pkcs12" || variant.platform == Platforms.IOS )
		{
			switch( true )
			{
				case variant.platform == Platforms.IOS && packageTarget == "ipa-app-store":
					subFolder = "store"
					break
				case variant.platform == Platforms.IOS && packageTarget == "ipa-ad-hoc":
					subFolder = "adhoc"
					break

				case variant.platform == Platforms.IOS:
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
		if( signingProvisioningProfile != "null" && variant.platform == Platforms.IOS ) list.add( project.file( signingProvisioningProfile ) )

		return list.reverse( )
	}
}
