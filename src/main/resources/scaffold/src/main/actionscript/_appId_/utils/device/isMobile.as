package _appId_.utils.device
{
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function isMobile() : Boolean
	{
		return isAndroid() || isIOS();
	}
}
