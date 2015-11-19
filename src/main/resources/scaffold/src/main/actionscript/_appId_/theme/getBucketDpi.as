package _appId_.theme
{
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function getBucketDpi( bucket : String ) : uint
	{
		switch( bucket )
		{
			case DpiBucket.LDPI :
				return 120;
			case DpiBucket.MDPI :
				return 160;
			case DpiBucket.HDPI :
				return 240;
			case DpiBucket.XHDPI :
				return 320;
			case DpiBucket.XXHDPI :
				return 480;
			case DpiBucket.XXXHDPI :
				return 640;
		}

		return 0;
	}
}
