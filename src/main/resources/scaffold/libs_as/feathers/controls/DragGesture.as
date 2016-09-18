/*
Feathers
Copyright 2012-2016 Bowler Hat LLC. All Rights Reserved.

This program is free software. You can redistribute and/or modify it in
accordance with the terms of the accompanying license agreement.
*/
package feathers.controls
{
	/**
	 * Gestures used to drag content.
	 */
	public class DragGesture
	{
		/**
		 * The target may be dragged in the appropriate direction from any
		 * location within its bounds.
		 */
		public static const CONTENT:String = "content";

		/**
		 * The target may be dragged in the appropriate direction starting from
		 * near the target's edge.
		 */
		public static const EDGE:String = "edge";

		/**
		 * No gesture can be used to drag the target.
		 */
		public static const NONE:String = "none";
	}
}
