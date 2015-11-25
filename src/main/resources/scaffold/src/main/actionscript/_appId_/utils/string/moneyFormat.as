package _appId_.utils.string
{
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function moneyFormat( n : Number , floatSeparator : String = "." , thousSeparator : String = " " ) : String
	{
		var float : String = String( n.toFixed( 2 ) ).slice( -2 );
		var s : String = String( n ).split( "." )[ 0 ];
		var aSplit : Array = [];

		while( s.slice( -3 ).length == 3 )
		{
			aSplit.push( thousSeparator + s.slice( -3 ) );
			s = s.substr( 0 , s.length - 3 );
		}

		aSplit.reverse();

		s = s + aSplit.join( "" ) + floatSeparator + float;

		if( s.charAt( 0 ) == thousSeparator ) s = s.substr( 1 );

		return s;
	}
}
