package org.bitbucket.xadkile.isp.ide.jupyterclient.kernel

class KernelImp(val cf:KernelConnectionFileContent, val p : Process) : Kernel{
    override fun stop() {
        p.destroy()
    }

}
