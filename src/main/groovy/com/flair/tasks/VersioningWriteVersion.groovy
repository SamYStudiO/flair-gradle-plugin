package com.flair.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO on 24/11/2015.
 */
public class VersioningWriteVersion extends DefaultTask
{
	public VersioningWriteVersion()
	{
		group = "versioning"
		description = ""
	}

	@TaskAction
	public void writeVersion()
	{
		String moduleName = project.flair.moduleName
		String version = project.flair.appVersion

		if( version.isEmpty( ) ) throw new IllegalArgumentException( String.format( "Missing appVersion add%nflair {%n\\appVersion = \"x.x.x\"%n}%nto your build.gradle file." ) )

		String[] a = version.split( "\\." )

		String major = a.length > 0 ? a[ 0 ] : "0"
		String minor = a.length > 1 ? a[ 1 ] : "0"
		String build = a.length > 2 ? a[ 2 ] : "0"

		FileTree tree = project.fileTree( "${ moduleName }/src/main/" )
		tree.each { file ->

			if( file.getText( ).indexOf( "<application xmlns=\"http://ns.adobe.com/air/application/" ) > 0 ) writeApp( file , "${ major }.${ minor }.${ build }" )
		}

		File file = project.file( "${ moduleName }/${ moduleName }.iml" )
		String content = file.getText( )
		content = content.replaceAll( /_[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}/ , "_${ major }.${ minor }.${ build }" )
		file.write( content )
	}

	protected void writeApp( File app , String version )
	{
		String content = app.getText( )

		content = content.replaceAll( /<versionNumber>.*<\\/versionNumber>/ , "<versionNumber>${ version }</versionNumber>" )

		app.write( content )
	}
}
