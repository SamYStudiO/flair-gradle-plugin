package _appId_.utils
{
	import flash.utils.Dictionary;
	import flash.utils.getQualifiedClassName;

	import starling.utils.StringUtil;

	/**
	 *
	 */
	public function stringify( o : Object ) : String
	{
		var ref : Dictionary = new Dictionary( true );
		ref[ "___id___" ] = -1;

		function _stringify( o : Object , tab : String = "  " ) : String
		{
			if( ref[ o ] == undefined )
				ref[ o ] = "#" + ( ++ref[ "___id___" ] );

			var startTab : String = tab;
			var s : String = "\n" + tab + "{ " + ref[ o ] + "\n";
			var hasProp : Boolean;

			tab += "  ";

			for( var prop : String in o )
			{
				hasProp = true;

				var value : * = o[ prop ];
				var a : Array = getQualifiedClassName( value ).split( "::" );
				var className : String = a.length > 1 ? a[ 1 ] : a[ 0 ];

				switch( true )
				{
					case value is Number :
					case value is String :
					case value is Date :
					case value is Boolean :
						s += tab + "- " + prop + " : " + className + " = " + value.toString() + "\n";
						break;
					case value is XML :
					case value is XMLList :
						s += tab + "- " + prop + " : " + className + " = " + XMLList( value ).toXMLString().split( "\n" ).join( "" ) + "\n";
						break;
					default :
						if( ref[ value ] == undefined ) s += tab + "- " + prop + " : " + className + " = " + _stringify( value , tab + "  " );
						else s += tab + "- " + prop + " : " + className + " = { " + ref[ value ] + " }\n";
						break;
				}
			}

			if( !hasProp ) return StringUtil.trim( s ) + " }\n";

			return s + startTab + "}\n";
		}

		return "[$stringify" + _stringify( o ) + "]";
	}
}

