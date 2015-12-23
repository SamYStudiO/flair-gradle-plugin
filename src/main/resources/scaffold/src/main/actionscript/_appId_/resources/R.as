package _appId_.resources
{
	/**
	 * DO NOT edit this class, this is auto generated from generateResourceClass task and will give you typed access to all app resources.
	 * This class try to mimic android R class.
	 */
	public final class R
	{
		/**
		 *
		 */
		public static var bool : RBool;

		/**
		 *
		 */
		public static var color : RColor;

		/**
		 *
		 */
		public static var dimen : RDimen;

		/**
		 *
		 */
		public static var drawable : RDrawable;

		/**
		 *
		 */
		public static var integer : RInteger;

		/**
		 *
		 */
		public static var object : RObject;

		/**
		 *
		 */
		public static var raw : RRaw;

		/**
		 *
		 */
		public static var sound : RSound;

		/**
		 *
		 */
		public static var string : RString;

		/**
		 *
		 */
		public static var xml : RXml;

		/**
		 *
		 */
		public function R( singleton : Singleton )
		{
			bool = new RBool();
			color = new RColor();
			dimen = new RDimen();
			drawable = new RDrawable();
			integer = new RInteger();
			object = new RObject();
			raw = new RRaw();
			sound = new RSound();
			string = new RString();
			xml = new RXml();
		}
	}
}

import _appId_.resources.R;
import _appId_.resources.getString;

new R( new Singleton() );

class Singleton
{

}

class RBool
{

}

class RColor
{

}

class RDimen
{

}

class RDrawable
{

}

class RInteger
{

}

class RObject
{

}

class RRaw
{

}

class RSound
{

}

class RString
{
	public const first_screen : String = getString( "first_screen" );
	public const hello : String = getString( "hello" );
	public const world : String = getString( "world" );
}

class RXml
{

}
