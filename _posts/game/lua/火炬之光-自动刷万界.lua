function OnEvent(event, arg)
    OutputLogMessage("event = %s, arg = %s\n", event, arg)
    if(event == 'MOUSE_BUTTON_PRESSED' and arg==2 ) then
        -- 1.������ͼ
        openMap()
        -- 2.�ȴ�10s������Ϸ
        Sleep(10*1000)
        -- 3.�ͷż���
        releaseSkill()
        -- 4.��ǰ�ƶ�����boss
        attackBoss()
        -- 5.�ȴ�boss����ɱ
        Sleep(60*1000)
        -- 6.ʰȡ��Ʒ
        pickup()
        -- 7.�س�
        returnCity()
    end
end

--[[
   ������ͼ
]]
function openMap()
    -- 1.����D��
    PressKey('d')
    if(IsModifierPress('d')) then
    ReleaseKey('d')
    end
    PressAndReleaseKey('d')
    -- 2.�ƶ���굽����
    MoveMouseTo(31828,39661)
    -- 3.���������
    PressMouseButton(1)
    if(IsMouseButtonPressed(1)) then
    ReleaseMouseButton(1)
    end

    -- 4.�ƶ���ȷ�ϵ�ͼ
    MoveMouseTo(32033,56060)
    -- 5.���������
    PressMouseButton(1)
    if(IsMouseButtonPressed(1)) then
    ReleaseMouseButton(1)
    end
end

--[[
   �ͷż���
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
��ǰ�ƶ�����boss
]]
function attackBoss()
    -- 1.�ƶ���굽����
    MoveMouseTo(32767,32767)
    -- 2.���������
    PressMouseButton(1)
end

--[[
ʰȡ��Ʒ
]]
function pickup()

end

--[[
�س�
]]
function returnCity()
end