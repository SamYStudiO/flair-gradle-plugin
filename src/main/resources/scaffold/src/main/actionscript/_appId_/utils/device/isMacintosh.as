package _appId_.utils.device
{
	import flash.system.Capabilities;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function isMacintosh() : Boolean
	{
		return Capabilities.manufacturer.toLowerCase().indexOf( "macintosh" ) >= 0;
	}
}
