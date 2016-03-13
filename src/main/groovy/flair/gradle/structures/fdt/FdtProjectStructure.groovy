package flair.gradle.structures.fdt

import flair.gradle.structures.IStructure
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class FdtProjectStructure implements IStructure
{
	@Override
	public void create( Project project , File source )
	{
		project.copy {
			from "${ source.path }/fdt/project_template.xml"
			into project.file( "/" )

			rename "project_template.xml" , ".project"
		}

		project.copy {
			from "${ source.path }/fdt/org.eclipse.core.resources.prefs"
			into ".settings"
		}

		File f = project.file( ".project" )
		String content = f.text
		content = content.replace( "{projectName}" , project.name )
		f.write( content )
	}
}
