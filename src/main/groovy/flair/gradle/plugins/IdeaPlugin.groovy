package flair.gradle.plugins

import flair.gradle.dependencies.Sdk
import flair.gradle.structure.IStructure
import flair.gradle.structure.IdeaStructure
import org.apache.tools.ant.taskdefs.condition.Os

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class IdeaPlugin extends AbstractIdePlugin
{
	@Override
	public List<IStructure> getStructures()
	{
		List<IStructure> list = new ArrayList<IStructure>( )
		list.add( new IdeaStructure( ) )

		return list
	}

	@Override
	protected List<Sdk> findSdks()
	{
		String path = ""
		String file = ""

		switch( true )
		{
			case Os.isFamily( Os.FAMILY_WINDOWS ):
				path = "${ System.getProperty( "user.home" ) }/."
				break;

			case Os.isFamily( Os.FAMILY_UNIX ):
				path = "${ System.getProperty( "user.home" ) }/."
				break;

			case Os.isFamily( Os.FAMILY_MAC ):
				path = "${ System.getProperty( "user.home" ) }/"
				break;
		}

		switch( true )
		{
			case Os.isFamily( Os.FAMILY_WINDOWS ):
				file = "/config/options/jdk.table.xml"
				break;

			case Os.isFamily( Os.FAMILY_UNIX ):
				file = "/config/options/jdk.table.xml"
				break;

			case Os.isFamily( Os.FAMILY_MAC ):
				file = "/options/jdk.table.xml"
				break;
		}

		List<String> all = new ArrayList<String>( )
		all.add( internalGetSdkPath( project.file( "${ path }IntelliJIdea15${ file }" ) ) )
		all.add( internalGetSdkPath( project.file( "${ path }IntelliJIdea14${ file }" ) ) )
		all.add( internalGetSdkPath( project.file( "${ path }IntelliJIdea13${ file }" ) ) )
		all.add( internalGetSdkPath( project.file( "${ path }IntelliJIdea12${ file }" ) ) )
		all.add( internalGetSdkPath( project.file( "${ path }IntelliJIdea11${ file }" ) ) )
		all.add( internalGetSdkPath( project.file( "${ path }IntelliJIdea10${ file }" ) ) )

		int index = -1
		float currentVersion = 0

		all.eachWithIndex { it , i ->
			if( it.split( "\\|" ).size( ) == 3 )
			{
				float version = Float.parseFloat( it.split( "\\|" )[ 2 ] )

				if( version > currentVersion )
				{
					index = i
					currentVersion = version
				}
			}
		}

		if( all.size( ) && index != -1 )
		{
			String[] a = all[ index ].split( "\\|" )
			if( a.size( ) ) return [ new Sdk( a[ 1 ] , a[ 0 ] ) ]
		}

		return [ new Sdk( project ) ]
	}

	private String internalGetSdkPath( File file )
	{
		if( !file.exists( ) ) return ""

		Node node = new XmlParser( ).parse( file )

		NodeList list = node.component.jdk.findAll {

			it.type[ 0 ].@value.toString( ).contains( "Flex SDK" )
		}

		if( !list.size( ) ) return ""

		int index = 0
		float currentVersion = 0

		list.eachWithIndex { it , i ->

			String sVersion = it.version[ 0 ].@value.toString( ).replace( "AIR SDK " , "" )
			String[] a = sVersion.split( "\\." )
			sVersion = a[ 0 ] + "."

			a.eachWithIndex { entry , j -> if( j != 0 ) sVersion += entry
			}

			float version = Float.parseFloat( sVersion )

			if( version > currentVersion )
			{
				index = i
				currentVersion = version
			}
		}

		return list[ index ].children( ).find {
			it.name( ) == "name"
		}.@value.toString( ) + "|" + list[ index ].homePath[ 0 ].@value.toString( ) + "|" + currentVersion
	}
}
