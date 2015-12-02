package _appId_.utils.device
{
	import flash.system.Capabilities;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function isWindows() : Boolean
	{
		return Capabilities.manufacturer.toLowerCase().indexOf( "windows" ) >= 0;
	}
}
