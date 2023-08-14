function OnEvent(event, arg)
    OutputLogMessage("event = %s, arg = %s\n", event, arg)
    if(event == 'MOUSE_BUTTON_PRESSED' and arg==2 ) then
        -- 1.开启地图
        openMap()
        -- 2.等待10s加载游戏
        Sleep(10*1000)
        -- 3.释放技能
        releaseSkill()
        -- 4.向前移动触发boss
        attackBoss()
        -- 5.等待boss被击杀
        Sleep(60*1000)
        -- 6.拾取物品
        pickup()
        -- 7.回城
        returnCity()
    end
end

--[[
   开启地图
]]
function openMap()
    -- 1.按下D键
    PressKey('d')
    if(IsModifierPress('d')) then
    ReleaseKey('d')
    end
    PressAndReleaseKey('d')
    -- 2.移动鼠标到中心
    MoveMouseTo(31828,39661)
    -- 3.点击鼠标左键
    PressMouseButton(1)
    if(IsMouseButtonPressed(1)) then
    ReleaseMouseButton(1)
    end

    -- 4.移动到确认地图
    MoveMouseTo(32033,56060)
    -- 5.点击鼠标左键
    PressMouseButton(1)
    if(IsMouseButtonPressed(1)) then
    ReleaseMouseButton(1)
    end
end

--[[
   释放技能
]]
function releaseSkill()
    PressKey('q')
    if(IsModifierPress('q')) then
    ReleaseKey('q')
    end
    Sleep(1*1000)
    PressKey('q')
    if(IsModifierPress('q')) then
    ReleaseKey('q')
    end
    Sleep(1*1000)
    PressKey('q')
    if(IsModifierPress('q')) then
    ReleaseKey('q')
    end
    Sleep(1*1000)
    if(IsModifierPress('w')) then
    ReleaseKey('w')
    end
    Sleep(1*1000)
    if(IsModifierPress('w')) then
    ReleaseKey('w')
    end
    Sleep(1*1000)
    if(IsModifierPress('w')) then
    ReleaseKey('w')
    end
end

--[[
向前移动触发boss
]]
function attackBoss()
    -- 1.移动鼠标到中心
    MoveMouseTo(32767,32767)
    -- 2.点击鼠标左键
    PressMouseButton(1)
end

--[[
拾取物品
]]
function pickup()

end

--[[
回城
]]
function returnCity()
end