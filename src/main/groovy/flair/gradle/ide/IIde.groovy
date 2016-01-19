package flair.gradle.ide

import flair.gradle.structure.IStructure

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public interface IIde
{
	public IStructure getStructure()

	public boolean getIsActive()

	public void refresh()
}