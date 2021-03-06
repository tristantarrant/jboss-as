/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.as.ee.managedbean.component;

import org.jboss.as.ee.component.ComponentTypeInjectionSource;
import org.jboss.as.ee.component.EEModuleClassDescription;
import org.jboss.as.ee.component.EEModuleDescription;
import org.jboss.as.ee.component.InjectionSource;
import org.jboss.as.ee.component.InjectionTarget;
import org.jboss.as.ee.component.deployers.EEResourceReferenceProcessor;
import org.jboss.as.server.deployment.DeploymentPhaseContext;
import org.jboss.as.server.deployment.DeploymentUnitProcessingException;
import org.jboss.logging.Logger;

/**
 * User: jpai
 */
public class ManagedBeanResourceReferenceProcessor implements EEResourceReferenceProcessor {

    private static final Logger logger = Logger.getLogger(ManagedBeanResourceReferenceProcessor.class);

    private final String managedBeanClassName;

    public ManagedBeanResourceReferenceProcessor(final String managedBeanClassName) {
        if (managedBeanClassName == null || managedBeanClassName.trim().isEmpty()) {
            throw new IllegalArgumentException("Managed bean classname cannot be null or empty: " + managedBeanClassName);
        }
        this.managedBeanClassName = managedBeanClassName;
    }

    @Override
    public String getResourceReferenceType() {
        return this.managedBeanClassName;
    }

    @Override
    public InjectionSource getResourceReferenceBindingSource(DeploymentPhaseContext phaseContext, EEModuleDescription eeModuleDescription, EEModuleClassDescription classDescription, String resourceReferenceType, String localContextName, InjectionTarget injectionTarget) throws DeploymentUnitProcessingException {
        logger.debug("Processing @Resource of type: " + this.managedBeanClassName + " for ENC name: " + localContextName);
        // ComponentType binding source for managed beans
        final InjectionSource bindingSource = new ComponentTypeInjectionSource(this.managedBeanClassName);
        return bindingSource;
    }
}
