package com.flair.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author Fabrique on 25/11/2015.
 */
class UpdateProperties extends DefaultTask
{
	public UpdateProperties()
	{
		group = "scaffold"
		description = ""
	}

	@TaskAction
	public void updateProperties()
	{
		String moduleName = project.flair.moduleName

		updatePropertiesFromFile( project.file( "${ moduleName }/src/main/resources/android/app_descriptor.xml" ) )
		updatePropertiesFromFile( project.file( "${ moduleName }/src/main/resources/ios/app_descriptor.xml" ) )
		updatePropertiesFromFile( project.file( "${ moduleName }/src/main/resources/desktop/app_descriptor.xml" ) , true )
	}

	private void updatePropertiesFromFile( File f )
	{
		updatePropertiesFromFile( f , false )
	}

	private void updatePropertiesFromFile( File f , Boolean desktop )
	{
		String appId = project.flair.appId
		String appAspectRatio = project.flair.appAspectRatio
		String appAutoOrient = project.flair.appAutoOrient
		String appDepthAndStencil = project.flair.appDepthAndStencil
		String appContent = f.getText( )

		if( desktop )
		{
			appContent = appContent.replaceAll( /<id>.*<\\/id>/ , "<id>${ appId }</id>" )
					.replaceAll( /<depthAndStencil>.*<\\/depthAndStencil>/ , "<depthAndStencil>${ appDepthAndStencil }</depthAndStencil>" )
		}
		else
		{
			appContent = appContent.replaceAll( /<id>.*<\\/id>/ , "<id>${ appId }</id>" )
					.replaceAll( /<aspectRatio>.*<\\/aspectRatio>/ , "<aspectRatio>${ appAspectRatio }</aspectRatio>" )
					.replaceAll( /<autoOrients>.*<\\/autoOrients>/ , "<autoOrients>${ appAutoOrient }</autoOrients>" )
					.replaceAll( /<depthAndStencil>.*<\\/depthAndStencil>/ , "<depthAndStencil>${ appDepthAndStencil }</depthAndStencil>" )
		}

		f.write( appContent )
	}
}
