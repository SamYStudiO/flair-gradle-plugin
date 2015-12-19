package _appId_.utils.device
{
	/**
	 *
	 */
	public function isMobile() : Boolean
	{
		return isAndroid() || isIOS();
	}
}
