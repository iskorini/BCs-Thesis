public class PolicySet_ReadWrite extends PolicySet {
	protected ContextRequest_Status ctxReq;
	public PolicySet_ReadWrite(ContextRequest_Status ctxReq) throws MissingAttributeException {
		this.ctxReq = ctxReq;
		addId("ReadWrite_Policy");
		addCombiningAlg(it.unifi.facpl.lib.algorithm.DenyUnlessPermitGreedy.class);
		ExpressionFunction e1 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class, "Bob",
				new AttributeName("name", "id"));
		ExpressionFunction e2 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class, "Alice",
				new AttributeName("name", "id"));
		ExpressionBooleanTree ebt = new ExpressionBooleanTree(ExprBooleanConnector.OR, e1, e2);
		addTarget(ebt);
		addPolicyElement(new PolicySet_Write(ctxReq));
		addPolicyElement(new PolicySet_Read(ctxReq));
		addPolicyElement(new PolicySet_StopWrite(ctxReq));
		addPolicyElement(new PolicySet_StopRead(ctxReq));
	}
	private class PolicySet_Write extends PolicySet {
		protected ContextRequest_Status ctxReq;
		public PolicySet_Write(ContextRequest_Status ctxReq) throws MissingAttributeException {
			this.ctxReq = ctxReq;
			addId("Write_Policy");
			addCombiningAlg(it.unifi.facpl.lib.algorithm.DenyUnlessPermitGreedy.class);
			ExpressionFunction e1 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class, 
					"file1",
					new AttributeName("file", "id")
					);
			ExpressionFunction e2 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class, 
					"write",
					new AttributeName("action", "id")
					);
			ExpressionBooleanTree ebt = new ExpressionBooleanTree(ExprBooleanConnector.AND, e1, e2);
			addTarget(ebt);
			addPolicyElement(new Rule_write());
			addObligation(new ObligationStatus(new FlagStatus(), Effect.PERMIT, ObligationType.M,
					ctxReq.getStatusAttribute(new StatusAttribute("isWriting", FacplStatusType.BOOLEAN)), true));
		}
		private class Rule_write extends Rule {
			Rule_write() throws MissingAttributeException {
				addId("write");
				addEffect(Effect.PERMIT);
				ExpressionFunction e1=new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						ctxReq.getStatusAttribute(
								ctxReq.getStatusAttribute(new StatusAttribute("isWriting", FacplStatusType.BOOLEAN))),
						false);
				ExpressionFunction e2=new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						ctxReq.getStatusAttribute(
								ctxReq.getStatusAttribute(new StatusAttribute("counterReadFile1", FacplStatusType.INT))),
						0);
				ExpressionBooleanTree ebt = new ExpressionBooleanTree(ExprBooleanConnector.AND, e1, e2);
				addTarget(ebt);
			}
		}
	}
	private class PolicySet_Read extends PolicySet {
		protected ContextRequest_Status ctxReq;
		public PolicySet_Read(ContextRequest_Status ctxReq) throws MissingAttributeException {
			this.ctxReq = ctxReq;
			addId("Read_Policy");
			addCombiningAlg(it.unifi.facpl.lib.algorithm.DenyUnlessPermitGreedy.class);
			ExpressionFunction e1 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class, 
					"file1",
					new AttributeName("file", "id")
					);
			ExpressionFunction e2 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class, 
					"read",
					new AttributeName("action", "id")
					);
			ExpressionBooleanTree ebt = new ExpressionBooleanTree(ExprBooleanConnector.AND, e1, e2);
			addTarget(ebt);
			addPolicyElement(new Rule_read());
			addObligation(new ObligationStatus(new AddStatus(), Effect.PERMIT, ObligationType.M,
					ctxReq.getStatusAttribute(new StatusAttribute("counterReadFile1", FacplStatusType.INT)), 1));
		}
		private class Rule_read extends Rule {
			Rule_read() throws MissingAttributeException {
				addId("read");
				addEffect(Effect.PERMIT);
				ExpressionFunction e1 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						ctxReq.getStatusAttribute(
								ctxReq.getStatusAttribute(new StatusAttribute("isWriting", FacplStatusType.BOOLEAN))),
						false);
				ExpressionFunction e2 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.LessThan.class,
						ctxReq.getStatusAttribute(
								ctxReq.getStatusAttribute(new StatusAttribute("counterReadFile1", FacplStatusType.INT))),
						2);
				ExpressionBooleanTree ebt = new ExpressionBooleanTree(ExprBooleanConnector.AND, e1, e2);
				addTarget(ebt);
			}
		}
	}
	private class PolicySet_StopRead extends PolicySet {
		protected ContextRequest_Status ctxReq;
		public PolicySet_StopRead(ContextRequest_Status ctxReq) throws MissingAttributeException {
			this.ctxReq = ctxReq;
			addId("StopRead_Policy");
			addCombiningAlg(it.unifi.facpl.lib.algorithm.DenyUnlessPermitGreedy.class);
			ExpressionFunction e1 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class, 
					"file1",
					new AttributeName("file", "id")
					);
			ExpressionFunction e2 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class, 
					"stopRead",
					new AttributeName("action", "id")
					);
			ExpressionBooleanTree ebt = new ExpressionBooleanTree(ExprBooleanConnector.AND, e1, e2);
			addTarget(ebt);
			addPolicyElement(new Rule_stopRead());
			addObligation(new ObligationStatus(new SubStatus(), Effect.PERMIT, ObligationType.M,
					ctxReq.getStatusAttribute(new StatusAttribute("counterReadFile1", FacplStatusType.INT)), 1));
		}
		private class Rule_stopRead extends Rule {
			Rule_stopRead() throws MissingAttributeException {
				addId("stopRead");
				addEffect(Effect.PERMIT);
				addTarget(new ExpressionFunction(it.unifi.facpl.lib.function.comparison.GreaterThan.class,
						ctxReq.getStatusAttribute(
								ctxReq.getStatusAttribute(new StatusAttribute("counterReadFile1", FacplStatusType.INT))),
						0));
			}
		}
	}
	
	private class PolicySet_StopWrite extends PolicySet {
		protected ContextRequest_Status ctxReq;
		public PolicySet_StopWrite(ContextRequest_Status ctxReq) throws MissingAttributeException {
			this.ctxReq = ctxReq;
			addId("StopWrite_Policy");
			addCombiningAlg(it.unifi.facpl.lib.algorithm.DenyUnlessPermitGreedy.class);
			ExpressionFunction e1 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class, 
					"file1",
					new AttributeName("file", "id")
					);
			ExpressionFunction e2 = new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class, 
					"stopWrite",
					new AttributeName("action", "id")
					);
			ExpressionBooleanTree ebt = new ExpressionBooleanTree(ExprBooleanConnector.AND, e1, e2);
			addTarget(ebt);
			addPolicyElement(new Rule_write());
			addObligation(new ObligationStatus(new FlagStatus(), Effect.PERMIT, ObligationType.M,
					ctxReq.getStatusAttribute(new StatusAttribute("isWriting", FacplStatusType.BOOLEAN)), false));
		}
		private class Rule_write extends Rule {
			Rule_write() throws MissingAttributeException {
				addId("stopWrite");
				addEffect(Effect.PERMIT);
				addTarget(new ExpressionFunction(it.unifi.facpl.lib.function.comparison.Equal.class,
						ctxReq.getStatusAttribute(
								ctxReq.getStatusAttribute(new StatusAttribute("isWriting", FacplStatusType.BOOLEAN))),
						true));
			}
		}
	}
}
