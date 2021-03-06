/*
 *******************************************************************************
 * Copyright (c) 2018 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package org.eclipse.microprofile.lra.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * LRA annoations support distributed communications amongst software
 * components and due to the unreliable nature of networks,
 * messages/requests can be lost, delayed or duplicated etc and the
 * implementation component responsible for invoking {@link Compensate}
 * and {@link Complete} annotated methods may loose track of the status of
 * a particpant. In this case, ideally it would just resend the completion
 * or compensation notication but if the participant (the class that
 * contains the Compensate and Complete annotations) does not
 * support idempotency then it must be able to report its' status by
 * by annotating one of the methods with this <em>@Status</em> annotation
 * together with the <em>@GET</em> JAX-RS annotation.
 * The annotated method should report the status according to one of the
 * {@link CompensatorStatus} enum values.
 *
 * If the participant has already responded successfully to an invocation
 * of the <em>@Compensate</em> or <em>@Complete</em> method then it may
 * report <em>404 Not Found</em> HTTP status code. This enables the
 * participant to free up resources.
 *
 * If this status method is invoked before either of the <em>@Compensate</em>
 * or <em>@Complete</em> methods have been called then the participant
 * should report that it does not yet have a status using a JAX-RS
 * exception mapper that maps to a <em>412 Precondition Failed</em> HTTP
 * status code (such as
 * {@link org.eclipse.microprofile.lra.client.IllegalLRAStateException}
 * but any exception that maps to 412 will do).
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Status {
}
