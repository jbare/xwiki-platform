/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
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
package org.xwiki.vfs.script;

import java.net.URI;
import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.xwiki.component.annotation.Component;
import org.xwiki.resource.ResourceReferenceSerializer;
import org.xwiki.script.service.ScriptService;
import org.xwiki.url.ExtendedURL;
import org.xwiki.vfs.internal.VfsResourceReference;

/**
 * Offers scripting APIs for the VFS module.
 *
 * @version $Id$
 * @since 7.4M2
 */
@Component
@Named("vfs")
@Singleton
//@Unstable
public class VfsScriptService implements ScriptService
{
    @Inject
    private ResourceReferenceSerializer<VfsResourceReference, ExtendedURL> serializer;

    /**
     * Generate a VFS URL to access a resource inside an archive.
     *
     * @param resourceReference the string representation of a VFS resource reference which defines the location of an
     *        archive. For example {@code attach:space.page@my.zip}.
     * @param pathInArchive the path of the resource inside the archive for which to generate a URL for. For example
     *        {@code /some/path/in/archive/test.txt}.
     * @return a URL that can be used to access the content of a file inside an archive (ZIP, EAR, TAR.GZ, etc)
     */
    public String url(String resourceReference, String pathInArchive)
    {
        try {
            VfsResourceReference vfsResourceReference = new VfsResourceReference(
                new URI(resourceReference), Arrays.asList(StringUtils.split(pathInArchive, "/")));
            return this.serializer.serialize(vfsResourceReference).toString();
        } catch (Exception e) {
            return null;
        }
    }
}
