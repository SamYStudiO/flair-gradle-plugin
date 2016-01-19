package flair.gradle.tasks

import flair.gradle.extensions.ConfigurationExtension
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class VersioningIncrementVersion extends AbstractVariantTask
{
	public VersioningIncrementVersion()
	{
		group = Group.DEFAULT.name
		description = ""
	}

	@TaskAction
	public void incrementVersion()
	{
		String version = extensionManager.getFlairProperty( ConfigurationExtension.APP_DESCRIPTOR.name , "version" , variant )

		if( version.isEmpty( ) ) throw new IllegalArgumentException( String.format( "Missing appVersion add%nflair {%n\\appVersion = \"x.x.x\"%n}%nto your build.gradle file." ) )

		String[] a = version.split( "\\." )

		int major = a.length > 0 ? Integer.parseInt( a[ 0 ] ) : 0
		int minor = a.length > 1 ? Integer.parseInt( a[ 1 ] ) : 0
		int build = a.length > 2 ? Integer.parseInt( a[ 2 ] ) : 0

		build++

		if( build > 999 )
		{
			build = 0
			minor++
		}

		if( minor > 9 )
		{
			major++
			minor = 0
		}

		File file = project.file( "build.gradle" )
		String content = file.getText( )
		content = content.replaceAll( /(appVersion\s*=\s*)"([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3})"/ , "\$1\"${ major }.${ minor }.${ build }\"" )
		file.write( content )
	}
}