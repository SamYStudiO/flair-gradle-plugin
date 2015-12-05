package _appId_.utils.displayMetrics
{
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function getDensityDpiFromPhysicalDensity( density : uint ) : int
	{
		switch( true )
		{
			case density <= 140 :
				return DENSITY_LDPI; // .75
			case density <= 180 :
				return DENSITY_MDPI; // 1.0
			case density <= 260 :
				return DENSITY_HDPI; // 1.5
			case density <= 300 :
				return DENSITY_280; // 1.75
			case density <= 340 :
				return DENSITY_XHDPI; // 2
			case density <= 380 :
				return DENSITY_360; // 2.25
			case density <= 420 :
				return DENSITY_400; // 2.5
			case density <= 440 :
				return DENSITY_420; // 2.625
			case density <= 500 :
				return DENSITY_XXHDPI; // 3
			case density <= 580 :
				return DENSITY_560; // 3.5
		}

		return DENSITY_XXXHDPI; // 4
	}
}
