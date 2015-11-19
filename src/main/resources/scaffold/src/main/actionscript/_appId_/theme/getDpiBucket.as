package _appId_.theme
{
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function getDpiBucket( dpi : uint ) : String
	{
		switch( true )
		{
			case dpi <= 120 :
				return DpiBucket.LDPI;
			case dpi <= 160 :
				return DpiBucket.MDPI;
			case dpi <= 240 :
				return DpiBucket.HDPI;
			case dpi <= 320 :
				return DpiBucket.XHDPI;
			case dpi <= 480 :
				return DpiBucket.XXHDPI;
		}

		return DpiBucket.XXXHDPI;
	}
}
