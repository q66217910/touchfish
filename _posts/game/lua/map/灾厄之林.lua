function OnEvent(event, arg)
    OutputLogMessage("event = %s, arg = %s\n", event, arg)
     if(event == 'MOUSE_BUTTON_RELEASED' and arg==5 ) then
                x, y = GetMousePosition();
                OutputLogMessage("Mouse is at %d, %d\n", x, y);
     end
    if(event == 'MOUSE_BUTTON_RELEASED' and arg==4 ) then
        -- 1.������ͼ���ƶ�����ͼ
        openMap()
        -- 2.�ȴ���ͼ����
        Sleep(5*1000)
        -- 3.�ͷż���
        releaseSkill()
        -- 4.��ͼ�ƶ���BOSS
        attackBoss()
        -- 6.ʰȡ��Ʒ
        -- pickup()
        -- 7.�س�
        returnCity()
    end
end

--[[
   1.������ͼ���ƶ�����ͼ
]]
function openMap()
    OutputLogMessage("start open map \n ")
    -- 1.��D��
    PressAndReleaseKey("d")
    Sleep(500)
    -- 2.ѡ����������
    move(49348,20468)

    -- 3.�ƶ��ֶ�֮��
    move(14070,32676)

    -- 4.�����һ��
    move(53412,56789)

    -- 5.ȷ�ϵ�ͼ
    move(53412,56789)

    OutputLogMessage("end open map \n ")

    -- 6.�����ƶ�
    MoveMouseTo(37839,27696)
    Sleep(500)
    PressMouseButton(1)
    Sleep(100)
    ReleaseMouseButton(1)
    Sleep(500)

    -- 7.�����ͼ
    PressAndReleaseKey("d")
end



--[[
   �ͷż���
]]
function releaseSkill()
    PressKey('q')
    Sleep(100)
    ReleaseKey('q')
    Sleep(500)

    PressKey('q')
    Sleep(100)
    ReleaseKey('q')
    Sleep(500)

    PressKey('q')
    Sleep(100)
    ReleaseKey('q')
    Sleep(500)

    PressKey('w')
    Sleep(100)
    ReleaseKey('w')
    Sleep(500)

    PressKey('w')
    Sleep(100)
    ReleaseKey('w')
    Sleep(500)

    PressKey('w')
    Sleep(100)
    ReleaseKey('w')
    Sleep(500)
end

--[[
��ͼ�ƶ���BOSS�����汾��
]]
function attackBoss()
    move(21310, 19679)
    move(24827, 27817)
    move(11099, 23687)
    move(10006, 42576)
    move(9835, 36199)
    move(15743, 28243)
    move(20490, 22169)
    move(21412, 13301)
    move(34970, 4616)
    move(29472, 4798)
    move(17451, 6742)
    move(37634, 4737)
    move(21071, 5466)
    move(46581, 13605)
    move(22574, 6924)
    move(14856, 50472)
    move(11475, 29214)
    move(33775, 6134)
    move(10279, 49379)
    move(9767, 34681)
    move(29745, 9171)
    move(42552, 26663)
    move(15675, 7349)
    move(15675, 7349)
    move(23052, 11540)
end

--[[
ʰȡ��Ʒ
]]
function pickup()
    for i=0,5,1
    do
       PressKey('a')
       Sleep(100)
       ReleaseKey('a')
       Sleep(500)
    end
end

--[[
�س�
]]
function returnCity()
    PressKey('t')
    Sleep(100)
    ReleaseKey('t')

    Sleep(2500)

    PressKey('d')
    Sleep(100)
    ReleaseKey('d')
    Sleep(500)
end

--[[
�ƶ�
]]
function move(x,y)
	MoveMouseTo(x,y)
    Sleep(500)
    PressMouseButton(1)
    Sleep(100)
    ReleaseMouseButton(1)
    Sleep(1000)
end