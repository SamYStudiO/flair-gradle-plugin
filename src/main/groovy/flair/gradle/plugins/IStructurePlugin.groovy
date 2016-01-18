package flair.gradle.plugins

import flair.gradle.structure.IStructure

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IStructurePlugin
{
	public void addStructure( IStructure structure )

	public void updateStructures()
}