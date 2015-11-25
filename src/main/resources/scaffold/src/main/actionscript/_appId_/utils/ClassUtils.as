package _appId_.utils
{
	import flash.system.ApplicationDomain;
	import flash.utils.Dictionary;
	import flash.utils.getDefinitionByName;
	import flash.utils.getQualifiedClassName;

	/**
	 * ClassUtils class used flash.utils.describeType function to get informations from an object.
	 *
	 * @author SamYStudiO
	 */
	public final class ClassUtils
	{
		/**
		 *
		 */
		private static var __class : Dictionary = new Dictionary( true );

		/**
		 * Test if an object inherit or implement a class.
		 * @param o The object to test.
		 * @param clazz One or sevarals Class to test.
		 * @return true if object inherit or implement one of the Class passed in arguments.
		 */
		public static function isEither( o : * , ...clazz ) : Boolean
		{
			var l : uint = clazz.length;

			for( var i : uint = 0; i < l; i++ )
			{
				if( o is clazz[ i ] ) return true;
			}

			return false;
		}

		/**
		 * Get a class definition from name.
		 * @param definition The String definition.
		 * @param applicationDomain An alternative application domain when search for definition.
		 * @return The Class definition.
		 */
		public static function getClassByName( definition : String , applicationDomain : ApplicationDomain = null ) : Class
		{
			var Clazz : Class;

			try
			{
				Clazz = getDefinitionByName( definition ) as Class;
			}
			catch( error : ReferenceError )
			{
				if( applicationDomain != null )
				{
					Clazz = applicationDomain.getDefinition( definition ) as Class;
				}
				else throw error;
			}

			return Clazz;
		}

		/**
		 * Get an instance from a Class object.
		 * @param Clazz Class object from which get instance.
		 * @param arguments The arguments to pass to instance constructor.
		 */
		public static function getInstance( Clazz : Class , ...arguments : Array ) : *
		{
			var xml : XML = describeType( Clazz );

			if( xml.hasOwnProperty( "factory" ) ) xml = new XML( xml.child( "factory" ) );

			var constructorArgumentsLength : uint = xml.child( "constructor" ).child( "parameter" ).length();

			switch( constructorArgumentsLength )
			{
				case 0    :
					return new Clazz();
				case 1    :
					return new Clazz( arguments[ 0 ] );
				case 2    :
					return new Clazz( arguments[ 0 ] , arguments[ 1 ] );
				case 3    :
					return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] );
				case 4    :
					return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] );
				case 5    :
					return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] );
				case 6    :
					return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] );
				case 7    :
					return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] );
				case 8    :
					return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] );
				case 9    :
					return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] );
				case 10 :
					return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] , arguments[ 9 ] );
				case 11 :
					return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] , arguments[ 9 ] , arguments[ 10 ] );
				case 12 :
					return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] , arguments[ 9 ] , arguments[ 10 ] , arguments[ 11 ] );
				case 13 :
					return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] , arguments[ 9 ] , arguments[ 10 ] , arguments[ 11 ] , arguments[ 12 ] );
				case 14 :
					return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] , arguments[ 9 ] , arguments[ 10 ] , arguments[ 11 ] , arguments[ 12 ] , arguments[ 13 ] );
				case 15 :
					return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] , arguments[ 9 ] , arguments[ 10 ] , arguments[ 11 ] , arguments[ 12 ] , arguments[ 13 ] , arguments[ 14 ] );
				case 16 :
					return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] , arguments[ 9 ] , arguments[ 10 ] , arguments[ 11 ] , arguments[ 12 ] , arguments[ 13 ] , arguments[ 14 ] , arguments[ 15 ] );
				case 17 :
					return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] , arguments[ 9 ] , arguments[ 10 ] , arguments[ 11 ] , arguments[ 12 ] , arguments[ 13 ] , arguments[ 14 ] , arguments[ 15 ] , arguments[ 16 ] );
				case 18 :
					return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] , arguments[ 9 ] , arguments[ 10 ] , arguments[ 11 ] , arguments[ 12 ] , arguments[ 13 ] , arguments[ 14 ] , arguments[ 15 ] , arguments[ 16 ] , arguments[ 17 ] );
				case 19 :
					return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] , arguments[ 9 ] , arguments[ 10 ] , arguments[ 11 ] , arguments[ 12 ] , arguments[ 13 ] , arguments[ 14 ] , arguments[ 15 ] , arguments[ 16 ] , arguments[ 17 ] , arguments[ 18 ] );
				case 20 :
					return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] , arguments[ 9 ] , arguments[ 10 ] , arguments[ 11 ] , arguments[ 12 ] , arguments[ 13 ] , arguments[ 14 ] , arguments[ 15 ] , arguments[ 16 ] , arguments[ 17 ] , arguments[ 18 ] , arguments[ 19 ] );
				case 21 :
					return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] , arguments[ 9 ] , arguments[ 10 ] , arguments[ 11 ] , arguments[ 12 ] , arguments[ 13 ] , arguments[ 14 ] , arguments[ 15 ] , arguments[ 16 ] , arguments[ 17 ] , arguments[ 18 ] , arguments[ 19 ] , arguments[ 20 ] );
			}
		}

		/**
		 * Get an instance from a String defintion.
		 * @param definition String definition from which get instance.
		 * @param An alternative application domain when search for definition.
		 * @param arguments The arguments to pass to instance constructor.
		 */
		public static function getInstanceByName( definition : String , applicationDomain : ApplicationDomain = null , ...arguments : Array ) : *
		{
			return ClassUtils.getInstance.apply( ClassUtils , [ ClassUtils.getClassByName( definition , applicationDomain ) ].concat( arguments ) );
		}

		/**
		 * Test if an Object is dynamic. Return always true if an object is Class type since Class is dynamic.
		 * @return true if Object is dynamic.
		 */
		public static function isDynamic( o : * ) : Boolean
		{
			var xml : XML = describeType( o );

			return xml.@isDynamic == "true";
		}

		/**
		 * Test if object class is final.
		 * @return true if object class is final
		 */
		public static function isFinal( o : * ) : Boolean
		{
			var xml : XML = describeType( o );

			return xml.@isFinal == "true";
		}

		/**
		 * Test if an object inherit from a class.
		 * @param o The Object to test.
		 * @param Clazz The Class to test.
		 * @return true if Object inherit from Clazz.
		 */
		public static function extendsClass( o : * , Clazz : Class ) : Boolean
		{
			var xml : XML = describeType( o );

			if( xml.hasOwnProperty( "factory" ) ) xml = new XML( xml.child( "factory" ) );

			var s : String = getQualifiedClassName( Clazz );

			return xml.child( "extendsClass" ).( @type == s ).length() >= 0;
		}

		/**
		 * Test if an object implement an interface.
		 * @param o The Object to test.
		 * @param InterfaceClazz The interface Class to test.
		 * @return true if Object implement from InterfaceClazz.
		 */
		public static function implementsInterface( o : * , InterfaceClazz : Class ) : Boolean
		{
			var xml : XML = describeType( o );

			if( xml.hasOwnProperty( "factory" ) ) xml = new XML( xml.child( "factory" ) );

			var s : String = getQualifiedClassName( InterfaceClazz );

			return xml.child( "implementsInterface" ).( @type == s ).length() >= 0;
		}

		/**
		 * Test if a property exist within the specified object.
		 * @param o The object where search for property.
		 * @param property The property to search.
		 * @return true if property exist within object.
		 */
		public static function hasProperty( o : * , property : String ) : Boolean
		{
			var xml : XML = describeType( o );

			if( xml.hasOwnProperty( "factory" ) ) xml = new XML( xml.child( "factory" ) );

			var hasAccessor : Boolean = xml.child( "accessor" ).( @name == property ).length() > 0;
			var hasVariable : Boolean = xml.child( "variable" ).( @name == property ).length() > 0;
			var hasMethod : Boolean = xml.child( "method" ).( @name == property ).length() > 0;
			var hasConstant : Boolean = xml.child( "constant" ).( @name == property ).length() > 0;

			return hasAccessor || hasVariable || hasMethod || hasConstant;
		}

		/**
		 * Test if an accessor exist within the specified object.
		 * @param o The object where search for accessor.
		 * @param accessor The accessor to search.
		 * @return true if accessor exist within object.
		 */
		public static function hasAccessor( o : * , accessor : String ) : Boolean
		{
			var xml : XML = describeType( o );

			if( xml.hasOwnProperty( "factory" ) ) xml = new XML( xml.child( "factory" ) );

			return xml.child( "accessor" ).( @name == accessor ).length() > 0;
		}

		/**
		 * Test if a writable accessor exist within the specified object.
		 * @param o The object where search for writable accessor.
		 * @param accessor The writable accessor to search.
		 * @return true if writable accessor exist within object.
		 */
		public static function hasWritableAccessor( o : * , accessor : String ) : Boolean
		{
			var xml : XML = describeType( o );

			if( xml.hasOwnProperty( "factory" ) ) xml = new XML( xml.child( "factory" ) );

			return xml.child( "accessor" ).( @name == accessor && @access == "readwrite" ).length() > 0;
		}

		/**
		 * Test if a variable exist within the specified object.
		 * @param o The object where search for variable.
		 * @param variable The variable to search.
		 * @return true if variable exist within object.
		 */
		public static function hasVariable( o : * , variable : String ) : Boolean
		{
			var xml : XML = describeType( o );

			if( xml.hasOwnProperty( "factory" ) ) xml = new XML( xml.child( "factory" ) );

			return xml.child( "variable" ).( @name == variable ).length() > 0;
		}

		/**
		 * Test if a variable or accessor exist within the specified object.
		 * @param o The object where search for variable or accessor.
		 * @param variableOrAccessor The variable or accessor to search.
		 * @return true if variable or accessor exist within object.
		 */
		public static function hasVariableOrAccessor( o : * , variableOrAccessor : String ) : Boolean
		{
			var xml : XML = describeType( o );

			if( xml.hasOwnProperty( "factory" ) ) xml = new XML( xml.child( "factory" ) );

			return xml.child( "variable" ).( @name == variableOrAccessor ).length() > 0 || xml.child( "accessor" ).( @name == variableOrAccessor ).length() > 0;
		}

		/**
		 * Test if a variable or writable accessor exist within the specified object.
		 * @param o The object where search for variable or writable accessor.
		 * @param variableOrAccessor The variable or writable accessor to search.
		 * @return true if variable or writable accessor exist within object.
		 */
		public static function hasWritableVariableOrAccessor( o : * , variableOrAccessor : String ) : Boolean
		{
			var xml : XML = describeType( o );

			if( xml.hasOwnProperty( "factory" ) ) xml = new XML( xml.child( "factory" ) );

			return xml.child( "variable" ).( @name == variableOrAccessor ).length() > 0 || xml.child( "accessor" ).( @name == variableOrAccessor && @access == "readwrite" ).length() > 0;
		}

		/**
		 * Test if a method exist within the specified object.
		 * @param o The object where search for method.
		 * @param method The method to search.
		 * @return true if method exist within object.
		 */
		public static function hasMethod( o : * , method : String ) : Boolean
		{
			var xml : XML = describeType( o );

			if( xml.hasOwnProperty( "factory" ) ) xml = new XML( xml.child( "factory" ) );

			return xml.child( "method" ).( @name == method ).length() > 0;
		}

		/**
		 * Clear describeType cache.
		 */
		public static function clearDescribeTypeCache() : void
		{
			__class = new Dictionary( true );
		}

		/**
		 * describetype is the same as flash.utils.describeType but use an internal cache in order to optimize multiple call to the same object since this can be expansive.
		 * The bad thing is cache increase memory consumption, if you have any memory problem you should use clearDescribeTypeCache method to flush cache.
		 *
		 * @see http://help.adobe.com/en_US/AS3LCR/Flash_10.0/flash/utils/package.html#describeType() flash.utils.describeType
		 * @see #clearDescribeTypeCache
		 */
		public static function describeType( o : * ) : XML
		{
			if( __class[ o ] == null ) __class[ o ] = flash.utils.describeType( o );

			return __class[ o ] as XML;
		}

		/**
		 * @private
		 */
		public function ClassUtils()
		{
			throw new Error( this + " cannot be instantiated" );
		}
	}
}
