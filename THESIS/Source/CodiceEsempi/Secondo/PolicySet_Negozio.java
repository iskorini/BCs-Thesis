package secondoEsempioStatus;

import it.unifi.facpl.lib.context.ContextRequest_Status;
import it.unifi.facpl.lib.enums.Effect;
import it.unifi.facpl.lib.enums.ExprBooleanConnector;
import it.unifi.facpl.lib.enums.FacplStatusType;
import it.unifi.facpl.lib.enums.ObligationType;
import it.unifi.facpl.lib.policy.ExpressionBooleanTree;
import it.unifi.facpl.lib.policy.ExpressionFunction;
import it.unifi.facpl.lib.policy.ObligationStatus;
import it.unifi.facpl.lib.policy.PolicySet;
import it.unifi.facpl.lib.policy.Rule;
import it.unifi.facpl.lib.util.AttributeName;
import it.unifi.facpl.lib.util.FacplDate;
import it.unifi.facpl.lib.util.exception.MissingAttributeException;
import it.unifi.facpl.system.status.StatusAttribute;
import it.unifi.facpl.system.status.functions.arithmetic.AddStatus;
import it.unifi.facpl.system.status.functions.arithmetic.SubStatus;
import it.unifi.facpl.system.status.functions.arithmetic.SumDateStatus;
import it.unifi.facpl.system.status.functions.strings.SetString;

public class PolicySet_Negozio extends PolicySet {
	public PolicySet_Negozio() throws MissingAttributeException {
		addId("NegozioMultimediale_Policy");
		// Algorithm Combining
		addCombiningAlg(it.unifi.facpl.lib.algorithm.DenyUnlessPermitGreedy.class);
		// Target
		ExpressionFunction e1 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class, "Alice",
				new AttributeName("name", "id"));
		ExpressionFunction e2 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class, "Bob",
				new AttributeName("name", "id"));

		ExpressionBooleanTree ebt = new ExpressionBooleanTree(ExprBooleanConnector.OR, e1, e2);

		addTarget(ebt);
		// Policy
		// AGIUNGERE POLICY
		addPolicyElement(new PolicySet_Buy());
		addPolicyElement(new PolicySet_NUMBER());
		addPolicyElement(new PolicySet_TIME());
		addPolicyElement(new PolicySet_VIEW());

	}

	private class PolicySet_Buy extends PolicySet {

		protected ContextRequest_Status ctxReq;

		public PolicySet_Buy() throws MissingAttributeException {
			
			addId("Buy_Policy");
			addCombiningAlg(it.unifi.facpl.lib.algorithm.DenyUnlessPermitGreedy.class);
			ExpressionFunction e1 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class, "file1",
					new AttributeName("file", "id"));
			ExpressionFunction e2 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class, "buy",
					new AttributeName("action", "id"));
			ExpressionBooleanTree ebt = new ExpressionBooleanTree(ExprBooleanConnector.AND, e1, e2);
			addTarget(ebt);
			addPolicyElement(new Rule_buy_alice());
			addPolicyElement(new Rule_buy_bob());
		}

		private class Rule_buy_alice extends Rule {

			Rule_buy_alice() throws MissingAttributeException {
				addId("buy");
				// Effect
				addEffect(Effect.PERMIT);
				ExpressionFunction e1 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						"buy", new AttributeName("action", "id"));
				ExpressionFunction e2 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						"Alice", new AttributeName("name", "id"));
				ExpressionBooleanTree ebt = new ExpressionBooleanTree(ExprBooleanConnector.AND, e1, e2);
				addTarget(ebt);
				addObligation(new ObligationStatus(new SetString(), Effect.PERMIT, ObligationType.M,
						new StatusAttribute("accessTypeAlice", FacplStatusType.STRING),
						"BUY"));
			}
		}

		private class Rule_buy_bob extends Rule {

			Rule_buy_bob() throws MissingAttributeException {
				addId("buy");
				// Effect
				addEffect(Effect.PERMIT);
				ExpressionFunction e1 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						"buy", new AttributeName("action", "id"));
				ExpressionFunction e2 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						"Bob", new AttributeName("name", "id"));
				ExpressionBooleanTree ebt = new ExpressionBooleanTree(ExprBooleanConnector.AND, e1, e2);
				addTarget(ebt);
				addObligation(new ObligationStatus(new SetString(), Effect.PERMIT, ObligationType.M,
						new StatusAttribute("accessTypeBob", FacplStatusType.STRING),
						"BUY"));
			}
		}

	}

	private class PolicySet_NUMBER extends PolicySet {

		protected ContextRequest_Status ctxReq;

		public PolicySet_NUMBER() throws MissingAttributeException {
			
			addId("Buy_Policy");
			addCombiningAlg(it.unifi.facpl.lib.algorithm.DenyUnlessPermitGreedy.class);
			ExpressionFunction e1 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class, "file1",
					new AttributeName("file", "id"));
			ExpressionFunction e2 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class, "number",
					new AttributeName("action", "id"));
			ExpressionBooleanTree ebt = new ExpressionBooleanTree(ExprBooleanConnector.AND, e1, e2);
			addTarget(ebt);
			addPolicyElement(new Rule_buy_alice());
			addPolicyElement(new Rule_buy_bob());
		}

		private class Rule_buy_alice extends Rule {

			Rule_buy_alice() throws MissingAttributeException {
				addId("buy");
				// Effect
				addEffect(Effect.PERMIT);
				ExpressionFunction e1 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						"number", new AttributeName("action", "id"));
				ExpressionFunction e2 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						"Alice", new AttributeName("name", "id"));
				ExpressionBooleanTree ebt = new ExpressionBooleanTree(ExprBooleanConnector.AND, e1, e2);
				addTarget(ebt);
				addObligation(new ObligationStatus(new SetString(), Effect.PERMIT, ObligationType.M,
						new StatusAttribute("accessTypeAlice", FacplStatusType.STRING),
						"NUMBER"));
				addObligation(new ObligationStatus(new AddStatus(), Effect.PERMIT, ObligationType.M,
						new StatusAttribute("aliceFile1viewNumber", FacplStatusType.INT),
						2));
			}
		}

		private class Rule_buy_bob extends Rule {

			Rule_buy_bob() throws MissingAttributeException {
				addId("buy");
				// Effect
				addEffect(Effect.PERMIT);
				ExpressionFunction e1 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						"number", new AttributeName("action", "id"));
				ExpressionFunction e2 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						"Bob", new AttributeName("name", "id"));
				ExpressionBooleanTree ebt = new ExpressionBooleanTree(ExprBooleanConnector.AND, e1, e2);
				addTarget(ebt);
				addObligation(new ObligationStatus(new SetString(), Effect.PERMIT, ObligationType.M,
						new StatusAttribute("accessTypeBob", FacplStatusType.STRING),
						"NUMBER"));
				addObligation(new ObligationStatus(new SetString(), Effect.PERMIT, ObligationType.M,
						new StatusAttribute("bobFile1viewNumber", FacplStatusType.INT),
						2));
			}
		}

	}

	private class PolicySet_TIME extends PolicySet {

		protected ContextRequest_Status ctxReq;

		public PolicySet_TIME() throws MissingAttributeException {
			addId("Buy_Policy");
			addCombiningAlg(it.unifi.facpl.lib.algorithm.DenyUnlessPermitGreedy.class);
			ExpressionFunction e1 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class, "file1",
					new AttributeName("file", "id"));
			ExpressionFunction e2 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class, "time",
					new AttributeName("action", "id"));
			ExpressionBooleanTree ebt = new ExpressionBooleanTree(ExprBooleanConnector.AND, e1, e2);
			addTarget(ebt);
			addPolicyElement(new Rule_buy_alice());
			addPolicyElement(new Rule_buy_bob());
		}

		private class Rule_buy_alice extends Rule {

			Rule_buy_alice() throws MissingAttributeException {
				addId("buy");
				// Effect
				addEffect(Effect.PERMIT);
				ExpressionFunction e1 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						"time", new AttributeName("action", "id"));
				ExpressionFunction e2 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						"Alice", new AttributeName("name", "id"));
				ExpressionBooleanTree ebt = new ExpressionBooleanTree(ExprBooleanConnector.AND, e1, e2);
				addTarget(ebt);
				addObligation(new ObligationStatus(new SetString(), Effect.PERMIT, ObligationType.M,
						new StatusAttribute("accessTypeAlice", FacplStatusType.STRING),
						"TIME"));

				addObligation(new ObligationStatus(new AddStatus(), Effect.PERMIT, ObligationType.M,
						new StatusAttribute("aliceFile1expiration", FacplStatusType.DATE),
						new FacplDate("0000/00/00-48:00:00")));
			}
		}

		private class Rule_buy_bob extends Rule {

			Rule_buy_bob() throws MissingAttributeException {
				addId("buy");
				// Effect
				addEffect(Effect.PERMIT);
				ExpressionFunction e1 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						"time", new AttributeName("action", "id"));
				ExpressionFunction e2 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						"Bob", new AttributeName("name", "id"));
				ExpressionBooleanTree ebt = new ExpressionBooleanTree(ExprBooleanConnector.AND, e1, e2);
				addTarget(ebt);
				addObligation(new ObligationStatus(new SetString(), Effect.PERMIT, ObligationType.M,
						new StatusAttribute("accessTypeBob", FacplStatusType.STRING),
						"TIME"));

				addObligation(new ObligationStatus(new SumDateStatus(), Effect.PERMIT, ObligationType.M,
						new StatusAttribute("bobFile1expiration", FacplStatusType.DATE),
						new FacplDate("0000/00/00-48:00:00")));
			}
		}

	}

	private class PolicySet_VIEW extends PolicySet {


		public PolicySet_VIEW() throws MissingAttributeException {
			addId("Buy_Policy");
			addCombiningAlg(it.unifi.facpl.lib.algorithm.DenyUnlessPermitGreedy.class);
			ExpressionFunction e1 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class, "file1",
					new AttributeName("file", "id"));
			ExpressionFunction e2 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class, "view",
					new AttributeName("action", "id"));
			ExpressionBooleanTree ebt = new ExpressionBooleanTree(ExprBooleanConnector.AND, e1, e2);
			addTarget(ebt);
			addPolicyElement(new Rule_BUY());
			addPolicyElement(new Rule_number_alice());
			addPolicyElement(new Rule_number_bob());
			addPolicyElement(new Rule_time_alice());
			addPolicyElement(new Rule_time_bob());
		}

		private class Rule_BUY extends Rule {
			Rule_BUY() throws MissingAttributeException {
				addId("buy");
				// Effect
				addEffect(Effect.PERMIT);
				ExpressionFunction e1 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						new StatusAttribute("accessTypeAlice", FacplStatusType.STRING), "BUY");
				ExpressionFunction e2 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						new StatusAttribute("accessTypeBob", FacplStatusType.STRING), "BUY");
				ExpressionBooleanTree ebt = new ExpressionBooleanTree(ExprBooleanConnector.OR, e1, e2 );
				addTarget(ebt);
			}
		}

		private class Rule_number_alice extends Rule {

			Rule_number_alice() throws MissingAttributeException {
				addId("buy");
				// Effect
				addEffect(Effect.PERMIT);
				ExpressionFunction e1 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.GreaterThan.class,
						new StatusAttribute("aliceFile1viewNumber", FacplStatusType.INT), 0);
				ExpressionFunction e2 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						"Alice", new AttributeName("name", "id"));
				ExpressionFunction e3 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						new StatusAttribute("accessTypeAlice", FacplStatusType.STRING), "NUMBER");
				ExpressionBooleanTree ebt = new ExpressionBooleanTree(ExprBooleanConnector.AND, e2, e3, e1);
				addTarget(ebt);
				addObligation(new ObligationStatus(new SubStatus(), Effect.PERMIT, ObligationType.M,
						new StatusAttribute("aliceFile1viewNumber", FacplStatusType.INT),
						1));
			}
		}

		private class Rule_number_bob extends Rule {

			Rule_number_bob() throws MissingAttributeException {
				addId("buy");
				// Effect
				addEffect(Effect.PERMIT);
				ExpressionFunction e1 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.GreaterThan.class,
						new StatusAttribute("bobFile1viewNumber", FacplStatusType.INT), 0);
				ExpressionFunction e2 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						"Bob", new AttributeName("name", "id"));
				ExpressionFunction e3 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						new StatusAttribute("accessTypeBob", FacplStatusType.STRING), "NUMBER");
				ExpressionBooleanTree ebt = new ExpressionBooleanTree(ExprBooleanConnector.AND, e2, e3, e1);
				addTarget(ebt);
				addObligation(new ObligationStatus(new SubStatus(), Effect.PERMIT, ObligationType.M,
						new StatusAttribute("bobFile1viewNumber", FacplStatusType.INT),
						1));
			}
		}
		
		
		
		private class Rule_time_alice extends Rule {

			Rule_time_alice() throws MissingAttributeException {
				addId("buy");
				// Effect
				addEffect(Effect.PERMIT);
				ExpressionFunction e1 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.GreaterThan.class,
						new StatusAttribute("aliceFile1expiration", FacplStatusType.DATE), new FacplDate("2016/04/20-00:00:00"));
				ExpressionFunction e2 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						"Alice", new AttributeName("name", "id"));
				ExpressionFunction e3 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						new StatusAttribute("accessTypeAlice", FacplStatusType.STRING), "TIME");
				ExpressionBooleanTree ebt = new ExpressionBooleanTree(ExprBooleanConnector.AND, e2, e3, e1);
				addTarget(ebt);
			}
		}
		
		private class Rule_time_bob extends Rule {

			Rule_time_bob() throws MissingAttributeException {
				addId("buy");
				// Effect
				addEffect(Effect.PERMIT);
				ExpressionFunction e1 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.GreaterThan.class,
						new StatusAttribute("bobFile1expiration", FacplStatusType.DATE), new FacplDate("2016/04/20-00:00:00"));
				ExpressionFunction e2 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						"Bob", new AttributeName("name", "id"));
				ExpressionFunction e3 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						new StatusAttribute("accessTypeBob", FacplStatusType.STRING), "TIME");
				ExpressionBooleanTree ebt = new ExpressionBooleanTree(ExprBooleanConnector.AND, e2, e3, e1);
				addTarget(ebt);
			}
		}
		
		

	}

}
