package com.simpsoft.salesCommission.app.model;

public enum RuleType {
	Simple {
		@Override
		public String toString() {
			return "Simple";
		}
	},
	Composite {
		@Override
		public String toString() {
			return "Composite";
		}
	}
}
