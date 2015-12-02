package _appId_.utils.device
{
	import flash.system.Capabilities;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function isLinux() : Boolean
	{
		return Capabilities.manufacturer.toLowerCase().indexOf( "linux" ) >= 0;
	}
}
